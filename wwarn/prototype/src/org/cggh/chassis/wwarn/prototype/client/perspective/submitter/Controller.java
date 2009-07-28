/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.perspective.submitter;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;


/**
 * @author aliman
 *
 */
class Controller {

	private Model model;
	private SubmitterPerspective owner;
	private Logger log;

	Controller(Model model, SubmitterPerspective owner) {
		this.model = model;
		this.owner = owner;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Controller.class.getName());
	}


	void setMainWidget(String widgetName) {
		log.enter("setMainWidget");
		log.info("widgetName: "+widgetName);
		
		// TODO
		
		log.leave();
	}

	void setIsCurrentPerspective(boolean b) {
		log.enter("setIsCurrent");

		this.model.setIsCurrentPerspective(b);
		
		log.leave();
	}


}
