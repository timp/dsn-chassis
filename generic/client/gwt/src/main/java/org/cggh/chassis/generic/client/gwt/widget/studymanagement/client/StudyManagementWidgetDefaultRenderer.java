/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetAPI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class StudyManagementWidgetDefaultRenderer implements StudyManagementWidgetModelListener {

	//Expose view elements for testing purposes.
	final Label displayCreateStudyUI = new Label("Create Study");
	final Label displayViewAllStudiesUI = new Label("View All Studies");
	final Panel displayCanvas;
	final Panel menuCanvas;
	final DecoratedPopupPanel menuPopUp = new DecoratedPopupPanel(true);
	final DecoratedPopupPanel confirmLoseChangesPopup = new DecoratedPopupPanel(false);

	final private StudyManagementWidgetController controller;
	private String authorEmail;
	
	//child widgets made package private to allow parent widget to access them
	final CreateStudyWidgetAPI createStudyWidget; 
	final Panel createStudyWidgetCanvas = new SimplePanel();
	final ViewStudyWidgetAPI viewStudyWidget;
	final Panel viewStudyWidgetCanvas = new SimplePanel();	
	final ViewStudiesWidgetAPI viewStudiesWidget;
	final Panel viewStudiesWidgetCanvas = new SimplePanel();
	final EditStudyWidgetAPI editStudyWidget;
	final Panel editStudyWidgetCanvas = new SimplePanel();

	
	public StudyManagementWidgetDefaultRenderer(Panel menuCanvas, 
												Panel displayCanvas,
												StudyManagementWidgetController controller,
												String authorEmail) {
		this.menuCanvas = menuCanvas;
		this.displayCanvas = displayCanvas;
		this.controller = controller;
		this.authorEmail = authorEmail;
		
		//create child widgets
		viewStudyWidget = new ViewStudyWidget(viewStudyWidgetCanvas);
		createStudyWidget = new CreateStudyWidget(createStudyWidgetCanvas);
		viewStudiesWidget = new ViewStudiesWidget(viewStudiesWidgetCanvas);
		editStudyWidget = new EditStudyWidget(editStudyWidgetCanvas);
		
		//initialise view
		initMenu();
		
	}

	private void initMenu() {
		
		//add click handlers to menu items
		displayCreateStudyUI.addClickHandler(new DisplayCreateStudyClickHandler());
		displayViewAllStudiesUI.addClickHandler(new DisplayViewAllStudiesClickHandler());
		
		//Create menu
		VerticalPanel menuItemsVerticalPanel = new VerticalPanel();
		menuItemsVerticalPanel.add(displayCreateStudyUI);
		menuItemsVerticalPanel.add(displayViewAllStudiesUI);
		
		//add menu to popupPanel
		menuPopUp.add(menuItemsVerticalPanel);
						
		//create menu header to display menu on click.
		final Label studiesMenuLabel = new Label("Studies");
		studiesMenuLabel.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				int popUpHOffset = studiesMenuLabel.getAbsoluteLeft();
				int popUpVOffset = studiesMenuLabel.getAbsoluteTop() + studiesMenuLabel.getOffsetHeight();
				menuPopUp.setPopupPosition(popUpHOffset, popUpVOffset);
				menuPopUp.show();
			}
			
		});
		
		//add menuHeader to menuCanvas
		menuCanvas.add(studiesMenuLabel);
		
	}
	
	class DisplayCreateStudyClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.displayCreateStudyWidget();
			menuPopUp.hide();
		}
		
	}
	
	class DisplayViewAllStudiesClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.displayViewAllStudiesWidget();
			menuPopUp.hide();
		}
		
	}
	
	public void onDisplayStatusChanged(Integer before, Integer after) {
		if (after == StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY) {
			displayCanvas.clear();
			createStudyWidget.setUpNewStudy(authorEmail);
			displayCanvas.add(createStudyWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(viewStudyWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES) {
			displayCanvas.clear();
			viewStudiesWidget.loadStudiesByAuthorEmail(authorEmail);
			displayCanvas.add(viewStudiesWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(editStudyWidgetCanvas);
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
					controller.displayViewAllStudiesWidget(true);
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

}
