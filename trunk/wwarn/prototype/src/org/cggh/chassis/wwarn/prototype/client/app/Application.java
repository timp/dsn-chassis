/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;


import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.twisted.client.Function;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

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
	
	
	

	public Deferred<Application> initialise() {
		log.enter("initialise");

		log.trace("create model");
		this.model = new Model();

		log.trace("create controller");
		this.controller = new Controller(this, model); 

		log.trace("create renderer");
		this.renderer = new Renderer(this, controller);
		model.addListener(renderer);
		
		log.trace("complete initialisation: refresh user details");
		Deferred<User> deferredUser = controller.refreshUserDetails();
		
		final Deferred<Application> deferredApplication = new Deferred<Application>();
		final Application self = this;
		
		log.trace("create callback chain");
		deferredUser.addCallback(new Function<User,Object>() {
			public Object apply(User user) {
				log.trace("user callback, callback deferred application");
				deferredApplication.callback(self);
				return null;
			}
		});
		
		log.leave();
		return deferredApplication;
	}


	





	protected Deferred<FractalUIComponent> syncState() {
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
		Deferred<FractalUIComponent> def = new Deferred<FractalUIComponent>();
		
		log.trace("callback deferred value immediately");
		def.callback(this);
		
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




	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	
	


}
