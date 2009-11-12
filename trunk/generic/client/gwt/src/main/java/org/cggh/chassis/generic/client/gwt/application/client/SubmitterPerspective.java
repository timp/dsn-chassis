/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;


import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.submitter.home.client.SubmitterHomeWidget;
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

	
	
	

	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(SubmitterPerspective.class);
	}



	
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

	}




	
	/**
	 * 
	 */
	@Override
	protected void renderMenuBar() {
		log.enter("renderMenuBar");
		
		this.mainMenu.addItem("home", new Command() {
			public void execute() {
				setActiveChild(submitterHomeWidget);
			} 
		});
		
		this.studyMenu = this.studyManagementWidget.getMenu();
		this.mainMenu.addItem("studies", this.studyMenu);

		this.dataMenu = this.dataManagementWidget.getMenu();
		this.mainMenu.addItem("data", this.dataMenu);
		
		log.leave();
	}






	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		
		super.bindUI();

		// TODO rewrite to gwt event pattern
		this.studyManagementWidget.addListener(new StudyManagementWidgetPubSubAPI() {
			
			public void onStudyManagementMenuAction(StudyManagementWidget source) {
				setActiveChild(studyManagementWidget);
			}
			
			public void onStudyManagementDisplayStatusChanged(
					Boolean couldStatusContainUnsavedData) {
				// TODO 
			}
			
		});
		
	}

	
	
	
	
}
