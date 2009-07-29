/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

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
		setMainWidget(widgetName, true);
	}
	
	
	
	void setMainWidget(String widgetName, boolean waypoint) {
		log.enter("setMainWidget");
		log.info("widgetName: "+widgetName);
		
		this.model.setMainWidgetName(widgetName);
		
		if (waypoint) {
			this.owner.waypoint();
		}
		
		log.leave();
	}

	void setIsCurrentPerspective(boolean b) {
		log.enter("setIsCurrent");

		this.model.setIsCurrentPerspective(b);
		
		// TODO hack for now to fire widget name event
		if (b) {
	 		this.model.setMainWidgetName(this.model.getMainWidgetName());
		}
		
		log.leave();
	}



	void setDefault() {
		log.enter("setDefault");
		
		log.info("set main widget as home");
		this.setMainWidget(SubmitterPerspective.WIDGET_HOME, false);

		log.info("sync state key, in case any parents change state");
		this.owner.syncStateKey();
		
		log.leave();
	}


}
