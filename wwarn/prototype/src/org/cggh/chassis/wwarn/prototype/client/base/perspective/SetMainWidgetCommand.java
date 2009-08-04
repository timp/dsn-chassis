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

	
	
	private PerspectiveController controller;
	private String viewName;



	public SetMainWidgetCommand(PerspectiveController controller, String viewName) {
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
