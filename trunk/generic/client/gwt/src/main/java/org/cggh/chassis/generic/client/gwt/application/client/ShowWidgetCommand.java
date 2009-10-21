/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import com.google.gwt.user.client.Command;


/**
 * @author aliman
 *
 */
public class ShowWidgetCommand implements Command {

	
	
	private PerspectiveController controller;
	private String widgetName;



	public ShowWidgetCommand(PerspectiveController controller, String widgetName) {
		this.controller = controller;
		this.widgetName = widgetName;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	public void execute() {
		this.controller.show(this.widgetName);
	}

}
