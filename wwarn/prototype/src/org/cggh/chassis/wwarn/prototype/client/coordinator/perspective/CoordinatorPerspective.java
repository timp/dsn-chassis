/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.coordinator.perspective;

import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponentFactory;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.allreqs.CoordinatorWidgetAllSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.allsubmissions.CoordinatorWidgetAllSubmissions;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.approvedreqs.CoordinatorWidgetApprovedSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.apprsubmissions.CoordinatorWidgetSubmissionsApprovedForRelease;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.cursubmissions.CoordinatorWidgetSubmissionsUnderCuration;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.deniedreqs.CoordinatorWidgetDeniedSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.home.CoordinatorWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.pendingreqs.CoordinatorWidgetPendingSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;



/**
 * @author aliman
 *
 */
public class CoordinatorPerspective extends BasePerspective {


	
	private Renderer renderer;

	
	
	public CoordinatorPerspective() {
		super();
		this.log.setCurrentClass(CoordinatorPerspective.class.getName());
		this.init();
	}
	

	
	private void init() {
		log.enter("init");
		
		log.trace("register main components");
		FractalUIComponentFactory.register(CoordinatorWidgetHome.class.getName(), CoordinatorWidgetHome.creator);
		FractalUIComponentFactory.register(CoordinatorWidgetPendingSubmissionRequests.class.getName(), CoordinatorWidgetPendingSubmissionRequests.creator);
		FractalUIComponentFactory.register(CoordinatorWidgetApprovedSubmissionRequests.class.getName(), CoordinatorWidgetApprovedSubmissionRequests.creator);
		FractalUIComponentFactory.register(CoordinatorWidgetDeniedSubmissionRequests.class.getName(), CoordinatorWidgetDeniedSubmissionRequests.creator);
		FractalUIComponentFactory.register(CoordinatorWidgetAllSubmissionRequests.class.getName(), CoordinatorWidgetAllSubmissionRequests.creator);
		FractalUIComponentFactory.register(CoordinatorWidgetSubmissionsUnderCuration.class.getName(), CoordinatorWidgetSubmissionsUnderCuration.creator);
		FractalUIComponentFactory.register(CoordinatorWidgetSubmissionsApprovedForRelease.class.getName(), CoordinatorWidgetSubmissionsApprovedForRelease.creator);
		FractalUIComponentFactory.register(CoordinatorWidgetAllSubmissions.class.getName(), CoordinatorWidgetAllSubmissions.creator);

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



	@Override
	public String getDefaultMainWidgetName() {
		return CoordinatorWidgetHome.class.getName();
	}


}
