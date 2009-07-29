/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;



import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;



/**
 * @author aliman
 *
 */
public class Application extends FractalUIComponent {

	
	
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



	public Deferred initialise() {
		log.enter("initialise");

		log.trace("create model");
		this.model = new Model();

		log.trace("create controller");
		this.controller = new Controller(this, model); 

		log.trace("create renderer");
		this.renderer = new Renderer(this, controller);
		model.addListener(renderer);
		
		log.trace("complete initialisation: refresh user details");
		Deferred def = controller.refreshUserDetails();
		
		log.leave();
		return def;
	}


	





	protected Deferred syncState() {
		log.enter("syncState");

		if (this.stateKey == null) {

			log.trace("state key is null, override to defaults");
			this.controller.setDefault();

		}
		else {
			
			log.trace("set state as per key");
			this.syncCurrentRole();

		}
		
		log.trace("create deferred value");
		Deferred def = new Deferred();
		
		log.trace("callback deferred value immediately");
		def.callback(null);
		
		log.leave();
		return def;
	}



	private void syncCurrentRole() {
		log.enter("syncCurrentRole");
		
		if (stateKey.containsKey(CURRENTROLE)) {
			
			JSONString roleName = (JSONString) stateKey.get(CURRENTROLE);
			log.trace("found current role: "+roleName.stringValue());
			this.controller.setCurrentRole(roleName.stringValue(), false);

		}
		
		log.leave();
	}



	@Override
	protected void syncStateKey() {
		log.enter("syncStateKey");
		
		log.trace("sync current role: "+this.model.getCurrentRole());
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
