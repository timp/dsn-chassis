/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;


/**
 * @author aliman
 *
 */
class Controller {

	private Model model;
	private CuratorPerspective owner;
	private Logger log;


	Controller(Model model, CuratorPerspective owner) {
		this.model = model;
		this.owner = owner;
		this.log = new GWTLogger();
		log.setCurrentClass(Controller.class.getName());
	}

	void setIsCurrentPerspective(boolean b) {
		this.model.setIsCurrentPerspective(b);
	}

	void setMainWidget(String widgetName) {
		log.enter("setMainWidget");
		log.info("widgetName: "+widgetName);

		// TODO 
		
		log.leave();
	}


}
