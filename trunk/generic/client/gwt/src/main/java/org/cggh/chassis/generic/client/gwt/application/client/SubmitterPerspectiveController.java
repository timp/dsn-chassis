/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;




/**
 * @author aliman
 *
 */
public class SubmitterPerspectiveController implements PerspectiveController {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private SubmitterPerspectiveModel model;




	public SubmitterPerspectiveController(SubmitterPerspectiveModel model) {
		this.model = model;
	}
	
	
	
	/**
	 * 
	 */
	public void show(String widgetName) {
		log.enter("show");
		
		for (String name : this.model.getWidgetNames()) {
			this.model.setVisibile(name, false);

		}
		
		this.model.setVisibile(widgetName, true);
		
		log.leave();
	}

	
	
	
}
