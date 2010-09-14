/**
 * 
 */
package spike.questionnaire.study.client;


import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 *
 * Try opening http://localhost:8080/chassis-wwarn-client-gwt-test/spike/questionnaire/study/
 * typically viewed using hosted mode browser and local tomcat server.
 * 
 * 
 */
public class SpikeStudyQuestionnaireEntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(SpikeStudyQuestionnaireEntryPoint.class);

	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		loadQuestionnaire();
		
		log.leave();
	}

	
	
	private void loadQuestionnaire() {
		log.enter("loadQuestionnaire");

		String qloc = InputElement.as(DOM.getElementById("qloc")).getValue();
		log.debug("qloc: "+qloc);
		
		Deferred<XQuestionnaire> deferredQuestionnaire = XQuestionnaire.load(qloc);
		
		deferredQuestionnaire.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire questionnaire) {
				RootPanel qp = RootPanel.get("questionnaire");
				qp.clear();
				qp.add(questionnaire);
				questionnaire.init();
				RootPanel.get("loading").setVisible(false);
				return questionnaire;
			}
			
		});
		
		deferredQuestionnaire.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				Window.alert("an error has occurred: "+in.getLocalizedMessage());
				log.debug("errback", in);
				return in;
			}

		});

		log.leave();
	}



}
