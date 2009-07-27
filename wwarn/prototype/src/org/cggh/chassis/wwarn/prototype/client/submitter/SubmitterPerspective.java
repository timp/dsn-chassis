/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;

import com.google.gwt.core.client.GWT;


/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends HMVCComponent implements Perspective {

	private void log(String message, String context) {
		String output = SubmitterPerspective.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}

	public SubmitterPerspective() {
		this.init();
	}

	private void init() {
		String _ = "init";
		log("begin", _);

		log("init model", _);
		Model model = new Model();

		log("init controller", _);
		Controller controller = new Controller(model, this); 

		log("init renderer", _);
		Renderer renderer = new Renderer(controller);
		model.addListener(renderer);
		
		log("complete initialisation", _);
		controller.init();
		
		log("end", _);

	}

	public static final String NAME = "submitter";
	public static final String TOKEN_BASE = "submitter/";
	public static final String TOKEN_HOME = "home";
	public static final String TOKEN_NEWSTUDY = "newstudy";
	public static final String TOKEN_MYSTUDIES = "mystudies";
	public static final String TOKEN_ALLSTUDIES = "allstudies";
	public static final String TOKEN_NEWSUBMISSION = "newsubmission";
	public static final String TOKEN_MYSUBMISSIONS = "mysubmissions";
	public static final String TOKEN_NEWDATADICTIONARY = "newdatadictionary";
	public static final String TOKEN_MYDATADICTIONARIES = "mydatadictionaries";
	public static final String TOKEN_ALLDATADICTIONARIES = "alldatadictionaries";
	public static final String ROLENAME = "submitter";

	public String getHomeToken() {
		return this.getAbsoluteHistoryTokenBase() + TOKEN_HOME;
	}

	public final String getName() {
		return NAME;
	}

	public void setStateToken(String stateToken) {
		String _ = "setStateToken("+stateToken+")";
		log("begin",_);
		
		// TODO Auto-generated method stub
		
		log("end",_);
	}

	@Override
	public String getRelativeHistoryTokenBase() {
		return TOKEN_BASE;
	}

}
