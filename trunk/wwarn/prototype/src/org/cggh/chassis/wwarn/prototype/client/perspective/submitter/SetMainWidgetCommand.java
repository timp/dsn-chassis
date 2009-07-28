/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.perspective.submitter;

import com.google.gwt.user.client.Command;

/**
 * @author aliman
 *
 */
public class SetMainWidgetCommand implements Command {

	private Controller controller;
	private String viewName;



	SetMainWidgetCommand(Controller controller, String viewName) {
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
