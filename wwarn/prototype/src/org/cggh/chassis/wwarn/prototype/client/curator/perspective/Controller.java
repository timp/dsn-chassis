/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.wwarn.prototype.client.submitter.perspective.SubmitterPerspective;


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
		log.enter("setIsCurrentPerspective");
		
		log.trace("set current perspective on model");
		this.model.setIsCurrentPerspective(b);

		log.leave();
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
