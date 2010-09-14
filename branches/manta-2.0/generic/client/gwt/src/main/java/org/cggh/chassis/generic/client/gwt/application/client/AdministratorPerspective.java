/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import com.google.gwt.user.client.Command;

/**
 * @author aliman
 *
 */
public class AdministratorPerspective extends PerspectiveBase {

	
	
	
	
	private AdministratorHomeWidget administratorHomeWidget;

	
	
	
	
	@Override
	public void renderMainChildren() {

		this.administratorHomeWidget = new AdministratorHomeWidget();
		
		this.mainChildren.add(this.administratorHomeWidget);
		
		this.activeChild = this.administratorHomeWidget;

	}

	
	
	
	
	@Override
	public void renderMenuBar() {

		this.menu.addItem("home", new Command() {
			public void execute() {
				setActiveChild(administratorHomeWidget);
			} 
		});
		
	}

	
	
	
}
