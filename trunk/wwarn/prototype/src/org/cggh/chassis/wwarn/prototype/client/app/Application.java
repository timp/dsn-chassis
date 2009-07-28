/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;



import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;



/**
 * @author aliman
 *
 */
public class Application extends HMVCComponent {

	
	
	public static final String ELEMENTID_USERDETAILS = "userdetails";
	public static final String ELEMENTID_USERNAMELABEL = "usernamelabel";
	public static final String ELEMENTID_CURRENTPERSPECTIVELABEL = "currentperspective";
	private static final String CURRENTROLE = "currentRole";

	
	
	
	
	private Model model;
	private Controller controller;
	private Renderer renderer;
	private List<ApplicationEventListener> listeners = new ArrayList<ApplicationEventListener>();;
	
	
	
	public Application() {
		super();
		this.initLog();
	}
	
	
	
	public Application(Logger log) {
		super(log);
		this.initLog();
	}
	
	
	
	
	private void initLog() {
		this.log.setCurrentClass(Application.class.getName());
	}
	
	
	
	public void addListener(ApplicationEventListener listener) {
		this.listeners.add(listener);
	}



	public void initialise() {
		log.enter("initialise");

		log.info("create model");
		this.model = new Model();

		log.info("create controller");
		this.controller = new Controller(this, model); 

		log.info("create renderer");
		this.renderer = new Renderer(this, controller);
		model.addListener(renderer);
		
		log.info("complete initialisation: refresh user details");
		controller.refreshUserDetails();
		
		log.leave();		
	}


	





	protected void syncState() {
		log.enter("syncState");

		if (this.stateKey == null) {

			log.info("state key is null, override to defaults");
			this.controller.setDefault();

		}
		else {
			
			log.info("set state as per key");
			this.syncCurrentRole();

		}
		
		log.leave();
	}



	private void syncCurrentRole() {
		log.enter("syncCurrentRole");
		
		if (stateKey.containsKey(CURRENTROLE)) {
			
			JSONString roleName = (JSONString) stateKey.get(CURRENTROLE);
			log.info("found current role: "+roleName.stringValue());
			this.controller.setCurrentRole(roleName.stringValue(), false);

		}
		
		log.leave();
	}



	@Override
	protected void syncStateKey() {
		log.enter("syncStateKey");
		
		log.info("sync current role: "+this.model.getCurrentRole());
		this.stateKey = new JSONObject();
		JSONString value = new JSONString(this.model.getCurrentRole());
		this.stateKey.put(CURRENTROLE, value);
		
		log.leave();
	}



	void fireInitialisationSuccess() {
		for (ApplicationEventListener l : this.listeners) {
			l.onInitialisationSuccess();
		}
	}
	
	
	


}
