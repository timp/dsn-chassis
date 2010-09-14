/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.HttpCallbackBase;
import org.cggh.chassis.generic.async.client.HttpCanceller;
import org.cggh.chassis.generic.async.client.HttpDeferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public abstract class XSelectBase extends XFormControl {

	
	
	
	private static final String STYLENAME_LOADING = "loading";
	
	private Log log = LogFactory.getLog(this.getClass());
	protected Label readOnlyLabel;
	protected Label loadingLabel;
	protected Map<String,String> labels = new HashMap<String,String>();
	protected List<String> values = new ArrayList<String>();
	HttpDeferred<List<Element>> deferredItems = new HttpDeferred<List<Element>>();


	
	
	/**
	 * @param definition
	 * @param model
	 * @param readOnly 
	 */
	XSelectBase(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);
	}

	
	
	
	

	
	@SuppressWarnings("unchecked")
	protected void constructItemMap() {
		
		if (loadingLabel == null) {
			loadingLabel = new Label("loading...");
			loadingLabel.addStyleName(STYLENAME_LOADING);
			this.canvas.add(loadingLabel);
		}
		else {
			loadingLabel.setVisible(true);
		}
		
		deferredItems = new HttpDeferred<List<Element>>();
		
		List<Element> itemElements = XML.getElementsByTagName(definition, XQS.ELEMENT_ITEM);
		
		Element itemsElement = XML.getElementByTagName(definition, XQS.ELEMENT_ITEMS);
		if (itemsElement != null) {
			String itemsSrc = itemsElement.getAttribute(XQS.ATTR_SRC);
			if (itemsSrc != null) {
				constructItemMapAsync(itemsSrc, itemElements, deferredItems);
			}
			else {
				throw new XQuestionFormatException("bad items definition, missing src attribute");
			}
		}
		else {
			deferredItems.callback(itemElements); // callback immediately
		}
		
		deferredItems.addCallback(new Function() {

			public Object apply(Object in) {
				loadingLabel.setVisible(false);
				return in;
			}

		});
	}
	
	
	
	
	protected void addItems(List<Element> itemElements) {
		for (int index=0; index < itemElements.size() ; index++) {
			
			Element itemElement = itemElements.get(index);
			String itemLabel = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_LABEL);
			String itemValue = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_VALUE);

			if (itemLabel == null) {
				throw new XQuestionFormatException("bad item definition, found no label for item ["+index+"]");
			}
			
			if (itemValue == null) {
				itemValue = "";
//				throw new XQuestionFormatException("bad select1 definition, found no value for item ["+index+"]");
			}

			values.add(itemValue);
			labels.put(itemValue, itemLabel);
			
		}
	}







	private Deferred<List<Element>> constructItemMapAsync(String src, List<Element> itemElements, HttpDeferred<List<Element>> deferredItems) {
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(src));
		
		builder.setCallback(new ConstructItemMapAsyncCallback(deferredItems));
		
		try {
			Request r = builder.send();
			deferredItems.setCanceller(new HttpCanceller(r));
		}
		catch (Throwable t) {
			deferredItems.errback(t);
		}

		deferredItems.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				log.enter("[anon errback function] :: apply");
				log.debug("caught exception trying to fetch items: "+in.getLocalizedMessage(), in);
				log.leave();
				return null;
			}
			
		});
		
		return deferredItems;

	}
	
	
	
	

	private class ConstructItemMapAsyncCallback extends HttpCallbackBase {

		private Log log = LogFactory.getLog(this.getClass());
		private Deferred<List<Element>> result;
		
		private ConstructItemMapAsyncCallback(HttpDeferred<List<Element>> result) {
			super(result);
			this.result = result;
			this.expectedStatusCodes.add(200);
		}

		public void onResponseReceived(Request request, Response response) {
			log.enter("onResponseReceived");

			super.onResponseReceived(request, response);

			try {

				log.debug("check preconditions");
				checkStatusCode(request, response);
				
				log.debug("parse the response");
				Document doc = XMLParser.parse(response.getText());
				List<Element> itemElements = XML.getElementsByTagName(doc, XQS.ELEMENT_ITEM);
				
				log.debug("pass through result");
				this.result.callback(itemElements);
				
			} catch (Throwable t) {

				log.debug("pass through error");
				this.result.errback(t);

			}

			log.leave();
		}

	}
	
	





	
	


}
