/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;



import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;



/**
 * @author aliman
 *
 */
public class Application extends HMVCComponent {

	
	
	public static final String ELEMENTID_USERDETAILS = "userdetails";
	public static final String ELEMENTID_USERNAMELABEL = "usernamelabel";
	public static final String ELEMENTID_CURRENTPERSPECTIVELABEL = "currentperspective";
	public static final String TOKEN_UNAUTHORISED = "unauthorised";
	
	
	
	public Application() {
		this.init();
	}
	
	
	
	void init() {
		String _ = "init"; log("begin",_);

		log("create model",_);
		Model model = new Model();

		log("create controller",_);
		Controller controller = new Controller(this, model); 

		log("create renderer",_);
		Renderer renderer = new Renderer(this, controller);
		model.addListener(renderer);
		
		log("create history manager",_);
		HistoryManager<String> historyManager = new HistoryManager<String>(this);
		model.addListener(historyManager); // listen for model changes & update history
		History.addValueChangeHandler(historyManager); // listen to history & map to controller actions
		
		log("complete initialisation: refresh user details",_);
		controller.refreshUserDetails();
		
		log("end",_);		
	}
	
	
	
	/**
	 * @@TODO
	 * @param message
	 * @param context
	 */
	private void log(String message, String context) {
		String output = Application.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}



	@Override
	public String getRelativeHistoryTokenBase() {
		return null;
	}


}
