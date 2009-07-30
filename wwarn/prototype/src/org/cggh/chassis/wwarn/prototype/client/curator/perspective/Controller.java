/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;


/**
 * @author aliman
 *
 */
class Controller {

	private Model model;
	@SuppressWarnings("unused")
	private CuratorPerspective owner;
	private Logger log;


	Controller(Model model, CuratorPerspective owner) {
		this.model = model;
		this.owner = owner;
		this.log = new GWTLogger();
		log.setCurrentClass(Controller.class.getName());
	}

	void setIsCurrentPerspective(boolean b) {
		log.enter("setIsCurrentPerspective");
		
		log.trace("set current perspective on model");
		this.model.setIsCurrentPerspective(b);

		log.leave();
	}

	void setMainWidget(String widgetName) {
		log.enter("setMainWidget");
		log.trace("widgetName: "+widgetName);

		// TODO 
		
		log.leave();
	}


}
