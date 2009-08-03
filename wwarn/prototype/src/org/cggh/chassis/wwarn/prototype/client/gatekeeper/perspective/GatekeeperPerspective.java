/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.gatekeeper.perspective;

import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class GatekeeperPerspective extends BasePerspective {


	
	public static final String WIDGET_PENDINGSUBMISSIONREQUESTS = "pendingrequests";
	public static final String WIDGET_APPROVEDSUBMISSIONREQUESTS = "approvedrequests";
	public static final String WIDGET_DENIEDSUBMISSIONREQUESTS = "deniedrequests";
	public static final String WIDGET_ALLSUBMISSIONREQUESTS = "allrequests";

	
	
	private Renderer renderer;

	
	
	public GatekeeperPerspective() {
		super();
		this.log.setCurrentClass(GatekeeperPerspective.class.getName());
		this.init();
	}
	

	
	private void init() {
		log.enter("init");

		log.trace("init model");
		model = new BasePerspectiveModel();

		log.trace("init controller");
		controller = new BasePerspectiveController(model, this); 

		log.trace("init renderer");
		renderer = new Renderer(this, controller, model);
		model.addListener(renderer);
		
		log.leave();
	}


	
	public String getRoleName() {
		return RoleNames.GATEKEEPER;
	}


	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}


}
