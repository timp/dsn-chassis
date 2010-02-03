/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;


import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.HandlerRegistration;
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
	public void renderMainChildren() {

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
	public void renderMenuBar() {
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





	@Override
	protected void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
	
		HandlerRegistration a = this.studyManagementWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				dataManagementWidget.viewDataset(e.getEntry().getId());
				setActiveChild(dataManagementWidget);
			}
		});

		HandlerRegistration b = this.dataManagementWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				studyManagementWidget.viewStudy(e.getEntry().getId());
				setActiveChild(studyManagementWidget);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		
	}

	
	
	
}
