/**
 * 
 */
package spike.study.questionnaire.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeStudyQuestionnaireEntryPoint implements EntryPoint {


	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		loadQuestionnaire();
		
	}
	
	
	
	
	private void loadQuestionnaire() {
		
		final RootPanel qp = RootPanel.get("questionnaire");
		
		qp.add(new HTML("<p>Loading questionnaire...</p>"));

		String url = null; // TODO
		
		Deferred<XQuestionnaire> def = XQuestionnaire.load(url);
		
		def.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire in) {
				
				qp.clear();
				qp.add(in);
				in.init();

				return in;
			}

		});
		
		def.addErrback(new Function<Throwable, Throwable>() {

			public Throwable apply(Throwable in) {
				qp.clear();
				qp.add(new HTML("an error has occurred, see log for more details: "+in.getLocalizedMessage()));
				log.trace("load questionnaire errback", in);
				return in;
			}

		});

	}
	
	
	

}
