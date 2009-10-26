/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

import org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidget;

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
public class PerspectiveWidgetDefaultRenderer implements PerspectiveWidgetModelListener {

	//Expose view elements for testing purposes.
	final Panel activeChildWidgetDisplayCanvas = new SimplePanel();
	final Panel studyManagmentWidgetDisplayCanvas = new SimplePanel();
	final DecoratedPopupPanel confirmLoseChangesPopup = new DecoratedPopupPanel(false);
	
	//child widgets, package private to allow owner widget access.
	StudyManagementWidget studyManagmentWidget;
	SubmissionManagementWidget submissionManagementWidget;
	
	final private Panel canvas;
	final private PerspectiveWidgetController controller;
	

	public PerspectiveWidgetDefaultRenderer(Panel canvas, PerspectiveWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		//initialise child widgets
		studyManagmentWidget = new StudyManagementWidget(studyManagmentWidgetDisplayCanvas);
		submissionManagementWidget = new SubmissionManagementWidget();
		
		initCanvas();
	}

	private void initCanvas() {
		
		//create menu bar
		HorizontalPanel menuBar = new HorizontalPanel();
		
		menuBar.add(studyManagmentWidget.getMenuCanvas());
		menuBar.add(submissionManagementWidget.getMenuCanvas());

		//Add panels to canvas using vertical panel
		VerticalPanel displayCanvas = new VerticalPanel();
		displayCanvas.add(menuBar);
		displayCanvas.add(activeChildWidgetDisplayCanvas);
		
		
		canvas.add(displayCanvas);
		
	}

	public void onDisplayStatusChanged(Integer before, Integer after) {

		if ((after == PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET)
			|| (after == PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY)) {
			
			//reset other widgets
			submissionManagementWidget.resetWidget();
			
			activeChildWidgetDisplayCanvas.clear();
			activeChildWidgetDisplayCanvas.add(studyManagmentWidgetDisplayCanvas);
			
		} else if ((after == PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET)
				|| (after == PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY)) {
			
			//reset other widgets
			studyManagmentWidget.resetWidget();
			
			activeChildWidgetDisplayCanvas.clear();
			activeChildWidgetDisplayCanvas.add(submissionManagementWidget);
			
		}
		
	}

	public void userMightLoseChanges(final Integer userRequestedState) {
		
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
				
				if (userRequestedState == PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET) {
					confirmLoseChangesPopup.hide();
					controller.displayStudyManagementWidget(false, true);
				} else if (userRequestedState == PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY) {
					confirmLoseChangesPopup.hide();
					controller.displayStudyManagementWidget(true, true);
				} else if (userRequestedState == PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET) {
					confirmLoseChangesPopup.hide();
					controller.displaySubmissionManagementWidget(false, true);
				} else if (userRequestedState == PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY) {
					confirmLoseChangesPopup.hide();
					controller.displaySubmissionManagementWidget(true, true);					
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
