/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;

import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
public class CuratorPerspective extends HMVCComponent implements Perspective {

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

	public static final String TOKEN_BASE = "curator/";
	public static final String NAME = "curator";
	public static final String TOKEN_HOME = "home";
	public static final String TOKEN_MYSUBMISSION = "mysubmissions";
	public static final String TOKEN_ALLSUBMISSIONS = "allsubmissions";
	public static final String TOKEN_NEWSTANDARDDATADICTIONARY = "newstandarddatadictionary";
	public static final String TOKEN_ALLSTANDARDDATADICTIONARIES = "allstandarddatadictionaries";
	public static final String TOKEN_NEWRELEASECRITERIA = "newreleasecriteria";
	public static final String TOKEN_ALLRELEASECRITERIA = "allreleasecriteria";
	public static final String TOKEN_DELEGATENEWTASKS = "delegatenewtask";
	public static final String TOKEN_MYDELEGATEDTASKS = "mydelegatedtasks";
	public static final String ROLENAME = "curator";

	public String getHomeToken() {
		return this.getAbsoluteHistoryTokenBase() + TOKEN_HOME;
	}

	public String getName() {
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
