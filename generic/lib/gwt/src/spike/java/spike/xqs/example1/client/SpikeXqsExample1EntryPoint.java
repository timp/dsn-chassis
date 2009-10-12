/**
 * 
 */
package spike.xqs.example1.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import spike.xqs.lib.client.Questionnaire;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class SpikeXqsExample1EntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(this.getClass());
	private Questionnaire questionnaire;

	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		Button getDataButton = new Button();
		getDataButton.setText("get form data");
		getDataButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String data = questionnaire.getBinding().getElement().toString();
				PreElement dataElement = PreElement.as(RootPanel.get("data").getElement());
				dataElement.setInnerText(data);
			}
			
		});
		RootPanel.get("controls").add(getDataButton);
		
		try {
			
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "/spike/xqs/example1-questionnaire.xml");

			builder.setCallback(new RequestCallback() {

				public void onError(Request request, Throwable t) {

					log.trace("caught throwable: "+t.getLocalizedMessage(), t);

				}

				public void onResponseReceived(Request request, Response response) {

					log.trace("received content: "+response.getText());
					if (response.getStatusCode() == 200) {
						Document doc = XMLParser.parse(response.getText());
						questionnaire = new Questionnaire(doc);
						RootPanel.get("questionnaire").add(questionnaire);
					}
					
				}
				
				
			});
			
			builder.send();

		}
		catch (Throwable t) {
			
			log.trace("caught throwable: "+t.getLocalizedMessage(), t);
			
		}
		
		log.leave();
	}

}
