/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;


import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * @author aliman
 *
 */
public class SubmitterPerspective 
	extends PerspectiveBase {

	
	
	// utility fields
	private Log log = LogFactory.getLog(SubmitterPerspective.class);

	
	
	
	// UI fields
	private SubmitterHomeWidget submitterHomeWidget;
	private StudyManagementWidget studyManagementWidget;
	private DataManagementWidget dataManagementWidget;
	private MenuBar studyMenu, dataMenu;

	
	
	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.application.client.NewPerspectiveBase#renderMainChildren()
	 */
	@Override
	protected void renderMainChildren() {

		this.submitterHomeWidget = new SubmitterHomeWidget();
		this.studyManagementWidget = new StudyManagementWidget();
		this.dataManagementWidget = new DataManagementWidget();
		
		this.mainChildren.add(this.submitterHomeWidget);
		this.mainChildren.add(this.studyManagementWidget);
		this.mainChildren.add(this.dataManagementWidget);

		this.activeChild = this.submitterHomeWidget;
		this.memory.setChild(this.submitterHomeWidget.getMemory());

	}




	
	/**
	 * 
	 */
	@Override
	protected void renderMenuBar() {
		log.enter("renderMenuBar");
		
		this.menu.addItem("home", new Command() {
			public void execute() {
				setActiveChild(submitterHomeWidget);
			} 
		});
		
		this.studyMenu = this.studyManagementWidget.getMenu();
		this.menu.addItem("studies", this.studyMenu);

		this.dataMenu = this.dataManagementWidget.getMenu();
		this.menu.addItem("data", this.dataMenu);
		
		log.leave();
	}






	
}
