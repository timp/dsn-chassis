/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.StudyQuestionnaireWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class StudyManagementWidgetDefaultRenderer implements StudyManagementWidgetModelListener {

	
	
	
	//Expose view elements for testing purposes.
	Label displayCreateStudyUI = new Label("Create Study");
	Label displayViewAllStudiesUI = new Label("View My Studies");
	Panel displayCanvas;
	Panel menuCanvas;
//	DecoratedPopupPanel menuPopUp = new DecoratedPopupPanel(true);
	DecoratedPopupPanel confirmLoseChangesPopup = new DecoratedPopupPanel(false);

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private StudyManagementWidgetController controller;
	private String authorEmail;
	
	
	
	
	//child widgets made package private to allow parent widget to access them
	CreateStudyWidget createStudyWidget; 
	Widget createStudyWidgetCanvas = new FlowPanel();
	ViewStudyWidget viewStudyWidget;
	Widget viewStudyWidgetCanvas = new FlowPanel();	
	ViewStudiesWidget viewStudiesWidget;
	Widget viewStudiesWidgetCanvas = new FlowPanel();
	EditStudyWidget editStudyWidget;
	Widget editStudyWidgetCanvas = new FlowPanel();
	private MenuBar menu;
	private StudyManagementWidget owner;
	StudyQuestionnaireWidget studyQuestionnaireWidget;

	
	
	
	/**
	 * Construct a study management widget renderer, injecting the panel to use 
	 * as the widget's display canvas.
	 * 
	 * @param menuCanvas
	 * @param displayCanvas
	 * @param controller
	 * @param authorEmail
	 */
	public StudyManagementWidgetDefaultRenderer(StudyManagementWidget owner, 
												Panel menuCanvas, 
												Panel displayCanvas,
												StudyManagementWidgetController controller,
												String authorEmail) {

		this.owner = owner;
		this.menuCanvas = menuCanvas;
		this.displayCanvas = displayCanvas;
		this.controller = controller;
		this.authorEmail = authorEmail;
		
		//create child widgets
		this.viewStudyWidget = new ViewStudyWidget((Panel)this.viewStudyWidgetCanvas);
		this.createStudyWidget = new CreateStudyWidget((Panel)this.createStudyWidgetCanvas);
		this.viewStudiesWidget = new ViewStudiesWidget((Panel)this.viewStudiesWidgetCanvas, "view");
		this.editStudyWidget = new EditStudyWidget((Panel)this.editStudyWidgetCanvas);
		this.studyQuestionnaireWidget = new StudyQuestionnaireWidget();
		
		//initialise view
		initMenu();
		
	}

	
	
	
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
		this.createStudyWidget = new CreateStudyWidget();
		this.viewStudiesWidget = new ViewStudiesWidget("view");
		this.editStudyWidget = new EditStudyWidget();
		this.studyQuestionnaireWidget = new StudyQuestionnaireWidget();
		
		// override using composites
		this.viewStudyWidgetCanvas = this.viewStudyWidget;
		this.viewStudiesWidgetCanvas = this.viewStudiesWidget;
		this.createStudyWidgetCanvas = this.createStudyWidget;
		this.editStudyWidgetCanvas = this.editStudyWidget;
		
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

		String authorEmail = this.authorEmail; // default to legacy pattern
		if (authorEmail == null) {
			authorEmail = ChassisUser.getCurrentUserEmail(); // new pattern
		}

		if (after == StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY) {
			displayCanvas.clear();
			
			// TODO should this go here? coordination logic
			createStudyWidget.setUpNewStudy(authorEmail); 
			
			displayCanvas.add(createStudyWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(viewStudyWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES) {
			displayCanvas.clear();
			
			// TODO should this go here? coordination logic
			viewStudiesWidget.loadStudiesByAuthorEmail(authorEmail);

			displayCanvas.add(viewStudiesWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(editStudyWidgetCanvas);
		}
		else if (after == StudyManagementWidgetModel.DISPLAYING_STUDY_QUESTIONNAIRE) {
			
			this.displayCanvas.clear();
			this.displayCanvas.add(this.studyQuestionnaireWidget);

		}
		
	}

	
	
	
	public void onUserMightLoseChanges(final Integer userRequestedView) {

		confirmLoseChangesPopup.clear();
		
		//create message box
		VerticalPanel messagePanel = new VerticalPanel();
		
		messagePanel.add(new Label("Any unsaved data will be lost."));
		
		//create cancel button and ClickHandler
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				confirmLoseChangesPopup.hide();
			}
		});
		
		
		//create continue button
		Button continueButton = new Button("Continue anyway");
		continueButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				if (userRequestedView == StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY) {
					confirmLoseChangesPopup.hide();
					controller.displayCreateStudyWidget(true);
				} else if (userRequestedView == StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES) {
					confirmLoseChangesPopup.hide();
					controller.displayViewStudiesWidget(true);
				}
			}
		});
		
		//Create button panel
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(continueButton);
		buttonPanel.add(cancelButton);
		
		messagePanel.add(buttonPanel);
		
		confirmLoseChangesPopup.add(messagePanel);			

		confirmLoseChangesPopup.center();
		confirmLoseChangesPopup.show();		

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

	
	
	
}
