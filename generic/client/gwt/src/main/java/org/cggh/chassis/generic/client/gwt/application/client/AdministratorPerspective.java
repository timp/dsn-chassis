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

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.application.client.NewPerspectiveBase#renderMainChildren()
	 */
	@Override
	protected void renderMainChildren() {

		this.administratorHomeWidget = new AdministratorHomeWidget();
		
		this.mainChildren.add(this.administratorHomeWidget);
		
		this.activeChild = this.administratorHomeWidget;

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.application.client.NewPerspectiveBase#renderMenuBar()
	 */
	@Override
	protected void renderMenuBar() {

		this.menu.addItem("home", new Command() {
			public void execute() {
				setActiveChild(administratorHomeWidget);
			} 
		});
		
	}

	
	
	
}
