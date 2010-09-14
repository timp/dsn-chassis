/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.List;


import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.HttpCallbackBase;
import org.cggh.chassis.generic.async.client.HttpCanceller;
import org.cggh.chassis.generic.async.client.HttpDeferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class XQuestionnaire extends Composite {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Element definition;
	private XQuestionnaireModel model;
	private XQuestionnaireView view;
	private XQuestionnaire parentQuestionnaire;
	private String defaultPrefix;
	private String defaultNamespaceUri;
	private boolean repeatable;
	private XQuestionnaire previousSibling;
	private HandlerManager eventBus;

	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition) {

		this.definition = definition;
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, String defaultPrefix, String defaultNamespaceUri) {

		this.definition = definition;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent, String defaultPrefix, String defaultNamespaceUri) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent, String defaultPrefix, String defaultNamespaceUri, XQuestionnaire previousSibling) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		this.previousSibling = previousSibling;
		
		construct();

	}
	
	
	
	private void construct() {

		String repeatable = definition.getAttribute(XQS.ATTR_REPEATABLE);
		if (repeatable != null && XQS.YES.equals(repeatable)) {
			this.repeatable = true;
		}

		String defaultPrefix = definition.getAttribute(XQS.ATTR_DEFAULTPREFIX);
		if (defaultPrefix != null) {
			this.setDefaultPrefix(defaultPrefix);
		}
		else if (parentQuestionnaire != null) {
			this.setDefaultPrefix(parentQuestionnaire.getDefaultPrefix()); // inherit from parent
		}

		String defaultNamespaceUri = definition.getAttribute(XQS.ATTR_DEFAULTNAMESPACEURI);
		if (defaultNamespaceUri != null) {
			this.setDefaultNamespaceUri(defaultNamespaceUri);
		}
		else if (parentQuestionnaire != null) {
			this.setDefaultNamespaceUri(parentQuestionnaire.getDefaultNamespaceUri()); // inherit from parent
		}

		for (Element e : XML.elements(definition.getChildNodes())) {
			
			if (e.getTagName().equals(XQS.ELEMENT_MODEL)) {
				constructModel(e);
			}

			if (e.getTagName().equals(XQS.ELEMENT_VIEW)) {
				constructView(e);
			}
			
		}
		
		if (this.model == null) {
			String message = "bad questionnaire definition, no model was found";
			throw new XQuestionFormatException(message);
		}
		
		if (this.view == null) {
			String message = "questionnaire definition, no view was found";
			throw new XQuestionFormatException(message);
		}

		initWidget(this.view.getCanvas());

	}
	
	
	
	public HandlerManager getEventBus() {
		return eventBus;
	}
	
	
	
	public void init() {
		this.init(false);
	}
	
	
	
	public void init(boolean readOnly) {
		log.enter("init( "+readOnly+" )");
		
		model.init();

		// TODO move this to model.init() ?
		if (parentQuestionnaire != null) {
			
			eventBus = parentQuestionnaire.getEventBus();
			
			if (previousSibling != null) {
				parentQuestionnaire.getModel().addChild(model, previousSibling.getModel());
			}
			else {
				parentQuestionnaire.getModel().addChild(model);				
			}

		}
		else {
			
			eventBus = new HandlerManager(this);
			
		}

		view.init(readOnly);
		
		log.leave();
	}





	
	
	/**
	 * @param documentElement
	 */
	public void init(Element data) {
		this.init(data, false);
	}
	

	
	public void init(Element data, boolean readOnly) {
		this.model.init(data);

		// TODO move this to model.init() ?
		if (this.parentQuestionnaire != null) {
			
			eventBus = parentQuestionnaire.getEventBus();

			if (this.previousSibling != null) {
				this.parentQuestionnaire.getModel().addChild(this.model, this.previousSibling.getModel());
			}
			else {
				this.parentQuestionnaire.getModel().addChild(this.model);				
			}

		}
		else {
			
			eventBus = new HandlerManager(this);
			
		}

		this.view.init(data, readOnly);
	}





	
	
	/**
	 * 
	 */
	private void constructModel(Element modelDef) {
		
		if (this.model != null) {
			throw new XQuestionFormatException("bad questionnaire definition, found more than one model");
		}

		this.model = new XQuestionnaireModel(modelDef, this);

	}
	
	


	/**
	 * @param e 
	 * 
	 */
	private void constructView(Element viewDef) {

		if (this.view != null) {
			throw new XQuestionFormatException("bad questionnaire definition, found more than one view");
		}

		this.view = new XQuestionnaireView(viewDef, this);
		
	}
	
	


	public XQuestionnaireView getView() {
		return this.view;
	}
	
	
	
	public XQuestionnaireModel getModel() {
		return this.model;
	}




	/**
	 * @param defaultPrefix the defaultPrefix to set
	 */
	public void setDefaultPrefix(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
	}




	/**
	 * @return the defaultPrefix
	 */
	public String getDefaultPrefix() {
		return defaultPrefix;
	}




	/**
	 * @param defaultNamespaceUri the defaultNamespaceUri to set
	 */
	public void setDefaultNamespaceUri(String defaultNamespaceUri) {
		this.defaultNamespaceUri = defaultNamespaceUri;
	}




	/**
	 * @return the defaultNamespaceUri
	 */
	public String getDefaultNamespaceUri() {
		return defaultNamespaceUri;
	}

	
	
	public boolean isRepeatable() {
		return this.repeatable;
	}

	
	
	public XQuestionnaire clone() {
		log.enter("clone");
		
		log.debug("clone this XQuestion");
		XQuestionnaire clone = new XQuestionnaire(this.definition, this.parentQuestionnaire, this.defaultPrefix, this.defaultNamespaceUri, this);
		
		log.leave();
		return clone;
	}
	
	
	
	/**
	 * 
	 */
	public void repeat() {
		log.enter("repeat");
		
		if (this.repeatable && this.parentQuestionnaire != null) {
			
			log.debug("clone this XQuestion");
			XQuestionnaire clone = this.clone();
			
			log.debug("insert cloned XQuestion into parent view");
			this.parentQuestionnaire.getView().addQuestionnaire(clone, this);

		}
		
		log.leave();
	}


	
	public void remove() {
		log.enter("remove");
		
		if (this.repeatable && this.parentQuestionnaire != null) {
			
			log.debug("remove question from view");
			this.parentQuestionnaire.getView().removeQuestion(this);

			log.debug("remove question from model");
			this.parentQuestionnaire.getModel().removeChild(this.getModel());
			
		}
		
		log.leave();
	}




	public List<XQuestionnaire> getRepeats() {
		if (this.parentQuestionnaire != null) {
			List<XQuestionnaire> siblings = this.parentQuestionnaire.getView().getNestedQuestionnaires();
			List<XQuestionnaire> repeats = new ArrayList<XQuestionnaire>();
			for (XQuestionnaire q : siblings) {
				if (q.getDefinition() == this.definition) {
					repeats.add(q);
				}
			}
			return repeats;
		}
		return null;
	}




	private Element getDefinition() {
		return this.definition;
	}




	public static Deferred<XQuestionnaire> load(String decodedUrl) {
		final HttpDeferred<XQuestionnaire> d = new HttpDeferred<XQuestionnaire>();
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(decodedUrl));

		builder.setCallback(new LoadQuestionnaireCallback(d));	
		
		try {
			Request r = builder.send();
			d.setCanceller(new HttpCanceller(r));
		}
		catch (Throwable t) {
			d.errback(t);
		}
		
		return d;
	}
	
	
	
	private static class LoadQuestionnaireCallback extends HttpCallbackBase {

		private Log log = LogFactory.getLog(this.getClass());
		private Deferred<XQuestionnaire> result;
		
		private LoadQuestionnaireCallback(HttpDeferred<XQuestionnaire> result) {
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
				XQuestionnaire questionnaire = new XQuestionnaire(doc.getDocumentElement());
				
				log.debug("pass through result");
				this.result.callback(questionnaire);
				
			} catch (Throwable t) {

				log.debug("pass through error");
				this.result.errback(t);

			}

			log.leave();
		}

	}

	
	
	
	public static Deferred<Void> loadData(XQuestionnaire questionnaire, String decodedUrl) {
		
		final HttpDeferred<Void> d = new HttpDeferred<Void>();
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(decodedUrl));

		builder.setCallback(new LoadDataCallback(questionnaire, d));	
		
		try {
			Request r = builder.send();
			d.setCanceller(new HttpCanceller(r));
		}
		catch (Throwable t) {
			d.errback(t);
		}
		
		return d;
	}
	
	
	
	
	private static class LoadDataCallback extends HttpCallbackBase {

		private Log log = LogFactory.getLog(this.getClass());
		private Deferred<Void> result;
		private XQuestionnaire questionnaire;
		
		private LoadDataCallback(XQuestionnaire questionnaire, HttpDeferred<Void> result) {
			super(result);
			this.questionnaire = questionnaire;
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
				this.questionnaire.init(doc.getDocumentElement());
				
				log.debug("pass through result");
				this.result.callback(null);
				
			} catch (Throwable t) {

				log.debug("pass through error");
				this.result.errback(t);

			}

			log.leave();
		}

	}




	public static Deferred<Void> loadDataReadOnly(XQuestionnaire questionnaire,	String decodedUrl) {
		final HttpDeferred<Void> d = new HttpDeferred<Void>();
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(decodedUrl));

		builder.setCallback(new LoadDataReadOnlyCallback(questionnaire, d));	
		
		try {
			Request r = builder.send();
			d.setCanceller(new HttpCanceller(r));
		}
		catch (Throwable t) {
			d.errback(t);
		}
		
		return d;
	}
	
	
	
	private static class LoadDataReadOnlyCallback extends HttpCallbackBase {

		private Log log = LogFactory.getLog(this.getClass());
		private Deferred<Void> result;
		private XQuestionnaire questionnaire;
		
		private LoadDataReadOnlyCallback(XQuestionnaire questionnaire, HttpDeferred<Void> result) {
			super(result);
			this.questionnaire = questionnaire;
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
				this.questionnaire.init(doc.getDocumentElement(), true);
				
				log.debug("pass through result");
				this.result.callback(null);
				
			} catch (Throwable t) {

				log.debug("pass through error");
				this.result.errback(t);

			}

			log.leave();
		}

	}




}
