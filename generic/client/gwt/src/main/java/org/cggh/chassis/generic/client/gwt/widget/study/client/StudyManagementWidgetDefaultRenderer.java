/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class StudyManagementWidgetDefaultRenderer implements StudyManagementWidgetModelListener {

	
	
	
	//Expose view elements for testing purposes.
	Panel displayCanvas;
	Panel menuCanvas;

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private StudyManagementWidgetController controller;
	
	
	
	
	//child widgets made package private to allow parent widget to access them
	NewStudyWidget createStudyWidget; 
	ViewStudyWidget viewStudyWidget;
	MyStudiesWidget viewStudiesWidget;
	EditStudyWidget editStudyWidget;
	private MenuBar menu;
	private StudyManagementWidget owner;
	ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget;
	EditStudyQuestionnaireWidget editStudyQuestionnaireWidget;

	
	
	

	
	
	/**
	 * Construct a study management widget default renderer, allowing the 
	 * renderer to create its own display canvas.
	 * 
	 * @param controller
	 */
	public StudyManagementWidgetDefaultRenderer(StudyManagementWidget owner, StudyManagementWidgetController controller) {

		this.owner = owner;
		this.menuCanvas = new FlowPanel();
		this.displayCanvas = new FlowPanel();
		this.controller = controller;
		
		//create child widgets
		this.viewStudyWidget = new ViewStudyWidget();
		this.createStudyWidget = new NewStudyWidget();
		this.viewStudiesWidget = new MyStudiesWidget("view");
		this.editStudyWidget = new EditStudyWidget();
		this.viewStudyQuestionnaireWidget = new ViewStudyQuestionnaireWidget();
		this.editStudyQuestionnaireWidget = new EditStudyQuestionnaireWidget();
		
		//initialise view
		this.initMenu();
		
	}

	
	
	
	private void initMenu() {
		log.enter("initMenu");
		
		menu = new MenuBar(true);
		
		log.debug("construct new study menu item");

		Command newStudyCommand = new Command() { 
			public void execute() { 
				log.enter("[anon Command] :: execute");
				
				log.debug("call displayCreateStudyWidget on controller");
				controller.displayCreateStudyWidget();
				
				log.debug("fire menu action on owner");
				owner.fireOnStudyManagementMenuAction();
				
				log.leave();
			} 
		};

		MenuItem newStudyMenuItem = new MenuItem("new study", newStudyCommand );
		menu.addItem(newStudyMenuItem);
		
		log.debug("construct my studies menu item");
		
		Command viewStudiesCommand = new Command() { 
			public void execute() { 
				log.enter("[anon Command] :: execute");
				
				log.debug("call displayViewStudiesWidget on controller");
				controller.displayViewStudiesWidget(); 
				
				log.debug("fire menu action on owner");
				owner.fireOnStudyManagementMenuAction();
				
				log.leave();
			} 
		};

		MenuItem viewStudiesMenuItem = new MenuItem("my studies", viewStudiesCommand );
		menu.addItem(viewStudiesMenuItem);
		
		menuCanvas.add(menu); // only needed for legacy
		
		log.leave();
	}
	
	
	
	
	public void onDisplayStatusChanged(Integer before, Integer after) {

		String authorEmail = ChassisUser.getCurrentUserEmail();

		if (after == StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY) {
			displayCanvas.clear();
			
			// TODO should this go here? coordination logic
			createStudyWidget.setUpNewStudy(authorEmail); 
			
			displayCanvas.add(createStudyWidget);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(viewStudyWidget);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES) {
			displayCanvas.clear();
			
			// TODO should this go here? coordination logic
			viewStudiesWidget.loadStudiesByAuthorEmail(authorEmail);

			displayCanvas.add(viewStudiesWidget);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(editStudyWidget);
		}
		else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY_QUESTIONNAIRE) {
			
			this.displayCanvas.clear();
			this.displayCanvas.add(this.viewStudyQuestionnaireWidget);

		}
		else if (after == StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY_QUESTIONNAIRE) {
			
			this.displayCanvas.clear();
			this.displayCanvas.add(this.editStudyQuestionnaireWidget);

		}
		
	}

	
	
	/**
	 * @return
	 */
	public Panel getCanvas() {
		return this.displayCanvas;
	}




	/**
	 * @return
	 */
	public MenuBar getMenu() {
		return this.menu;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.client.StudyManagementWidgetModelListener#onUserMightLoseChanges(java.lang.Integer)
	 */
	public void onUserMightLoseChanges(Integer userRequestedView) {
		// TODO Auto-generated method stub
		
	}	

	
	
	
}
