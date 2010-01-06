/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidget;

import com.google.gwt.user.client.Command;

/**
 * @author aliman
 *
 */
public class GatekeeperPerspective extends PerspectiveBase {

	
	
	
	
	private GatekeeperHomeWidget gatekeeperHomeWidget;
	private SubmissionManagementWidget submissionManagementWidget;

	
	
	
	
	@Override
	public void renderMainChildren() {

		this.gatekeeperHomeWidget = new GatekeeperHomeWidget();
		this.submissionManagementWidget = new SubmissionManagementWidget();
		
		this.mainChildren.add(this.gatekeeperHomeWidget);
		this.mainChildren.add(this.submissionManagementWidget);
		
		this.activeChild = this.gatekeeperHomeWidget;

	}

	
	
	
	
	@Override
	public void renderMenuBar() {

		this.menu.addItem("home", new Command() {
			public void execute() {
				setActiveChild(gatekeeperHomeWidget);
			} 
		});
		
		this.menu.addItem("submissions", this.submissionManagementWidget.getMenu());
	}

	
	
	
}
