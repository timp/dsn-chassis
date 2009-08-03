/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.coordinator.perspective;

import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class CoordinatorPerspective extends BasePerspective {


	
	public static final String WIDGET_PENDINGSUBMISSIONREQUESTS = "coordinator-pendingrequests";
	public static final String WIDGET_APPROVEDSUBMISSIONREQUESTS = "coordinator-approvedrequests";
	public static final String WIDGET_DENIEDSUBMISSIONREQUESTS = "coordinator-deniedrequests";
	public static final String WIDGET_ALLSUBMISSIONREQUESTS = "coordinator-allrequests";
	public static final String WIDGET_SUBMISSIONSUNDERCURATION = "coordinator-submissionsundercuration";
	public static final String WIDGET_SUBMISSIONSAPPROVEDFORRELEASE = "coordinator-submissionsapprovedforrelease";
	public static final String WIDGET_ALLSUBMISSIONS = "coordinator-allsubmissions";

	
	
	private Renderer renderer;

	
	
	public CoordinatorPerspective() {
		super();
		this.log.setCurrentClass(CoordinatorPerspective.class.getName());
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
		return RoleNames.COORDINATOR;
	}


	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}


}
