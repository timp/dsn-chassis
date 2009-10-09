/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class ApplicationWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	private ApplicationWidgetModel model;

	public ApplicationWidgetController(ApplicationWidgetModel model) {
		this.model = model;
	}

	public void displayCoordinatorPerspective() {
		log.enter("displayCoordinatorPerspective");
		
		model.setStatus(ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE);
		
		log.leave();
	}

	public void displaySubmitterPerspective() {
		log.enter("displaySubmitterPerspective");
		
		model.setStatus(ApplicationWidgetModel.STATUS_SUBMITTER_PERSPECTIVE);
		
		log.leave();
	}

	public void displayCuratorPerspective() {
		log.enter("displayCuratorPerspective");
		
		model.setStatus(ApplicationWidgetModel.STATUS_CURATOR_PERSPECTIVE);
		
		log.leave();
	}

	public void displayGatekeeperPerspective() {
		log.enter("displayGatekeeperPerspective");
		
		model.setStatus(ApplicationWidgetModel.STATUS_GATEKEEPER_PERSPECTIVE);
		
		log.leave();
	}

	public void displayUserPerspective() {
		log.enter("displayUserPerspective");
		
		model.setStatus(ApplicationWidgetModel.STATUS_USER_PERSPECTIVE);
		
		log.leave();
	}

}
