/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.base.perspective;

import com.google.gwt.user.client.Command;


/**
 * @author aliman
 *
 */
public class SetMainWidgetCommand implements Command {

	
	
	private BasePerspectiveController controller;
	private String viewName;



	public SetMainWidgetCommand(BasePerspectiveController controller, String viewName) {
		this.controller = controller;
		this.viewName = viewName;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	public void execute() {
		this.controller.setMainWidget(this.viewName);
	}

}
