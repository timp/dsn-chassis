/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;


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
		log.trace("widgetName: "+widgetName);
		
		this.doSetMainWidget(widgetName);
		
		if (waypoint) {
			this.owner.waypoint(); // new history item
		} 
		else {
			this.owner.syncStateKey(); // sync state key passively, no new history item
		}
		
		log.leave();
	}
	
	
	
	private void doSetMainWidget(String widgetName) {
		log.enter("doSetMainWidget");
		
		this.model.setMainWidgetName(widgetName);		
		
		log.leave();
	}

	
	
	void setIsCurrentPerspective(boolean b) {
		log.enter("setIsCurrent");

		this.model.setIsCurrentPerspective(b);
		
		log.leave();
	}



	void setDefault() {
		log.enter("setDefault");
		
		log.trace("set main widget as home");
		this.setMainWidget(SubmitterPerspective.WIDGET_HOME, false);

		// TODO review this
		log.trace("sync state key, in case any parents or children change state");
		this.owner.syncStateKey();
		
		log.leave();
	}


}
