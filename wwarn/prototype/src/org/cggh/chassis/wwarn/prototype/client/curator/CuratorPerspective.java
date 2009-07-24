/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;

import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
public class CuratorPerspective implements Perspective {

	public CuratorPerspective() {
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

	private void log(String message, String context) {
		String output = CuratorPerspective.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}

	public static final String TOKEN_SEPARATOR = "/";
	public static final String TOKEN_BASE = "curator";
	public static final String NAME = "curator";
	public static final String TOKEN_HOME = TOKEN_BASE + TOKEN_SEPARATOR + "home";
	public static final String TOKEN_MYSUBMISSION = TOKEN_BASE + TOKEN_SEPARATOR + "mysubmissions";
	public static final String TOKEN_ALLSUBMISSIONS = TOKEN_BASE + TOKEN_SEPARATOR + "allsubmissions";
	public static final String TOKEN_NEWSTANDARDDATADICTIONARY = TOKEN_BASE + TOKEN_SEPARATOR + "newstandarddatadictionary";
	public static final String TOKEN_ALLSTANDARDDATADICTIONARIES = TOKEN_BASE + TOKEN_SEPARATOR + "allstandarddatadictionaries";
	public static final String TOKEN_NEWRELEASECRITERIA = TOKEN_BASE + TOKEN_SEPARATOR + "newreleasecriteria";
	public static final String TOKEN_ALLRELEASECRITERIA = TOKEN_BASE + TOKEN_SEPARATOR + "allreleasecriteria";
	public static final String TOKEN_DELEGATENEWTASKS = TOKEN_BASE + TOKEN_SEPARATOR + "delegatenewtask";
	public static final String TOKEN_MYDELEGATEDTASKS = TOKEN_BASE + TOKEN_SEPARATOR + "mydelegatedtasks";

	public String getHomeToken() {
		return TOKEN_HOME;
	}

	public String getName() {
		return NAME;
	}

	public String getTokenBase() {
		return TOKEN_BASE;
	}

	public void setStateToken(String stateToken) {
		String _ = "setStateToken("+stateToken+")";
		log("begin",_);
		
		// TODO Auto-generated method stub
		
		log("end",_);
	}


}
