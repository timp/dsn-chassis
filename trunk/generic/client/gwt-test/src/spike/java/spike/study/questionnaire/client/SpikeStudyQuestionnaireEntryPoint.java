/**
 * 
 */
package spike.study.questionnaire.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeStudyQuestionnaireEntryPoint implements EntryPoint {


	
	
	static {
		LogFactory.create = AllenSauerLog.create;
	}
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	protected XQuestionnaire questionnaire;
	boolean editable = true;

	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		initGetDataButton();
		
		initReadOnlyButton();
		
		loadQuestionnaire();
		
		log.leave();
	}
	
	
	
	
	private void initReadOnlyButton() {
		log.enter("initReadOnlyButton");

		final Button readOnlyButton = new Button();
		readOnlyButton.setText("view read-only");
		readOnlyButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				if (editable) {
					questionnaire.init(questionnaire.getModel().getElement(), true);
					editable = false;
					readOnlyButton.setText("view editable");
				}
				else {
					questionnaire.init(questionnaire.getModel().getElement(), false);
					editable = true;
					readOnlyButton.setText("view read-only");
				}
			}
			
		});
		RootPanel.get("controls").add(readOnlyButton);
		
		log.leave();
	}




	private void initGetDataButton() {
		log.enter("initGetDataButton");

		Button getDataButton = new Button();
		getDataButton.setText("get form data");
		getDataButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				String data = questionnaire.getModel().getElement().toString();
				PreElement dataElement = PreElement.as(RootPanel.get("data").getElement());
				dataElement.setInnerText(data);
			}
			
		});
		RootPanel.get("controls").add(getDataButton);
		
		log.leave();
	}




	private void loadQuestionnaire() {
		log.enter("loadQuestionnaire");
		
		final RootPanel qp = RootPanel.get("questionnaire");
		
		qp.add(pWidget("Loading questionnaire..."));

		String url = JsConfiguration.getStudyQuestionnaireUrl();
		
		Deferred<XQuestionnaire> def = XQuestionnaire.load(url);
		
		def.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire in) {
				
				qp.clear();
				qp.add(in);
				in.init();
				questionnaire = in;

				return in;
			}

		});
		
		def.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				qp.clear();
				qp.add(new HTML("an error has occurred, see log for more details: "+in.getLocalizedMessage()));
				log.debug("load questionnaire errback", in);
				return in;
			}

		});

		log.leave();
	}
	
	
	

}
