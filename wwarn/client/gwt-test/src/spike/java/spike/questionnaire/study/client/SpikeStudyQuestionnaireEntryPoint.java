/**
 * 
 */
package spike.questionnaire.study.client;


import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeStudyQuestionnaireEntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(SpikeStudyQuestionnaireEntryPoint.class);

	private XQuestionnaire questionnaire;

	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		loadQuestionnaire();
		
		log.leave();
	}

	
	
	private void loadQuestionnaire() {

		String qloc = InputElement.as(DOM.getElementById("qloc")).getValue();
		Deferred<XQuestionnaire> d = XQuestionnaire.load(qloc);
		
		d.addCallback(new Function<XQuestionnaire,XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire in) {
				RootPanel qp = RootPanel.get("questionnaire");
				qp.clear();
				qp.add(in);
				in.init();
				questionnaire = in;
				return in;
			}
			
		});
		
		d.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				Window.alert("an error has occurred: "+in.getLocalizedMessage());
				log.debug("errback", in);
				return in;
			}

		});

	}



}
