/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;

import com.google.gwt.core.client.GWT;


/**
 * @author aliman
 *
 */
public class SubmitterPerspective implements Perspective {

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
	public static final String TOKEN_SEPARATOR = "/";
	public static final String TOKEN_BASE = "submitter";
	public static final String TOKEN_HOME = TOKEN_BASE + TOKEN_SEPARATOR + "home";
	public static final String TOKEN_NEWSTUDY = TOKEN_BASE + TOKEN_SEPARATOR + "newstudy";
	public static final String TOKEN_MYSTUDIES = TOKEN_BASE + TOKEN_SEPARATOR + "mystudies";
	public static final String TOKEN_ALLSTUDIES = TOKEN_BASE + TOKEN_SEPARATOR + "allstudies";
	public static final String TOKEN_NEWSUBMISSION = TOKEN_BASE + TOKEN_SEPARATOR + "newsubmission";
	public static final String TOKEN_MYSUBMISSIONS = TOKEN_BASE + TOKEN_SEPARATOR + "mysubmissions";
	public static final String TOKEN_NEWDATADICTIONARY = TOKEN_BASE + TOKEN_SEPARATOR + "newdatadictionary";
	public static final String TOKEN_MYDATADICTIONARIES = TOKEN_BASE + TOKEN_SEPARATOR + "mydatadictionaries";
	public static final String TOKEN_ALLDATADICTIONARIES = TOKEN_BASE + TOKEN_SEPARATOR + "alldatadictionaries";

	public final String getHomeToken() {
		return TOKEN_HOME;
	}

	public final String getName() {
		return NAME;
	}

	public final String getTokenBase() {
		return TOKEN_BASE;
	}

	public void setStateToken(String stateToken) {
		String _ = "setStateToken("+stateToken+")";
		log("begin",_);
		
		// TODO Auto-generated method stub
		
		log("end",_);
	}

}
