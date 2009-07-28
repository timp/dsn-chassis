/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;



import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCHistoryManager;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;



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
		this.listeners .add(listener);
	}



	public void initialise() {
		log.setCurrentMethod("initialise");
		log.info("begin");

		log.info("create model");
		this.model = new Model();

		log.info("create controller");
		this.controller = new Controller(this, model); 

		log.info("create renderer");
		this.renderer = new Renderer(this, controller);
		model.addListener(renderer);
		
		log.info("complete initialisation: refresh user details");
		controller.refreshUserDetails();
		
		log.info("return");		
	}


	





	protected void syncState() {
		log.setCurrentMethod("syncState");
		log.info("begin");

		if (this.stateKey == null) {

			log.info("state key is null, override to defaults");
			this.controller.setDefault();

		}
		else {
			
			log.info("set state as per key");
			this.syncCurrentRole();

		}
		
		log.info("return");
	}



	private void syncCurrentRole() {
		if (stateKey.containsKey(CURRENTROLE)) {
			
			JSONString roleName = (JSONString) stateKey.get(CURRENTROLE);
			log.info("found current role: "+roleName.stringValue());
			this.controller.setCurrentRole(roleName.stringValue(), false);

		}
	}



	@Override
	protected void syncStateKey() {
		log.setCurrentMethod("syncStateKey");
		log.info("begin");
		
		log.info("sync current role: "+this.model.getCurrentRole());
		this.stateKey = new JSONObject();
		JSONString value = new JSONString(this.model.getCurrentRole());
		this.stateKey.put(CURRENTROLE, value);
		
		log.info("return");
	}



	void fireInitialisationSuccess() {
		for (ApplicationEventListener l : this.listeners) {
			l.onInitialisationSuccess();
		}
	}
	
	
	


}
