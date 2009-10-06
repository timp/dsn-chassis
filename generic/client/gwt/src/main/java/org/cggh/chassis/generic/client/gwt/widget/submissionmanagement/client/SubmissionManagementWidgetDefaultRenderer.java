/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.edit.client.EditSubmissionWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.edit.client.EditSubmissionWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidgetAPI;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidgetDefaultRenderer implements SubmissionManagementWidgetModelListener {

	//Expose view elements for testing purposes.
	final Label displayCreateSubmissionUI = new Label("Create Submission");
	final Label displayViewAllSubmissionsUI = new Label("View All Submissions");
	final Panel displayCanvas;
	final Panel menuCanvas;
	final DecoratedPopupPanel menuPopUp = new DecoratedPopupPanel(true);

	final private SubmissionManagementWidgetController controller;
	private String authorEmail;
	
	//child widgets made package private to allow parent widget to access them
	final CreateSubmissionWidgetAPI createSubmissionWidget; 
	final Panel createSubmissionWidgetCanvas = new SimplePanel();
	final ViewSubmissionWidgetAPI viewSubmissionWidget;
	final Panel viewSubmissionWidgetCanvas = new SimplePanel();	
	final ViewSubmissionsWidgetAPI viewAllSubmissionsWidget;
	final Panel viewAllSubmissionsWidgetCanvas = new SimplePanel();
	final EditSubmissionWidgetAPI editSubmissionWidget;
	final Panel editSubmissionWidgetCanvas = new SimplePanel();

	
	public SubmissionManagementWidgetDefaultRenderer(Panel menuCanvas,
													 Panel displayCanvas,
													 SubmissionManagementWidgetController controller, 
													 String authorEmail) {
		this.menuCanvas = menuCanvas;
		this.displayCanvas = displayCanvas;
		this.controller = controller;
		this.authorEmail = authorEmail;
		
		//create child widgets
		viewSubmissionWidget = new ViewSubmissionWidget(viewSubmissionWidgetCanvas);
		createSubmissionWidget = new CreateSubmissionWidget(createSubmissionWidgetCanvas, authorEmail);
		viewAllSubmissionsWidget = new ViewSubmissionsWidget(viewAllSubmissionsWidgetCanvas);
		editSubmissionWidget = new EditSubmissionWidget(editSubmissionWidgetCanvas, authorEmail);
		
		//initialise view
		initMenu();
		
	}

	private void initMenu() {
		
		//add click handlers to menu items
		displayCreateSubmissionUI.addClickHandler(new DisplayCreateSubmissionClickHandler());
		displayViewAllSubmissionsUI.addClickHandler(new DisplayViewAllSubmissionsClickHandler());
		
		//Create menu
		VerticalPanel menuItemsVerticalPanel = new VerticalPanel();
		menuItemsVerticalPanel.add(displayCreateSubmissionUI);
		menuItemsVerticalPanel.add(displayViewAllSubmissionsUI);
		
		//add menu to popupPanel
		menuPopUp.add(menuItemsVerticalPanel);
						
		//create menu header to display menu on click.
		final Label submissionsMenuLabel = new Label("Submissions");
		submissionsMenuLabel.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				int popUpHOffset = submissionsMenuLabel.getAbsoluteLeft();
				int popUpVOffset = submissionsMenuLabel.getAbsoluteTop() + submissionsMenuLabel.getOffsetHeight();
				menuPopUp.setPopupPosition(popUpHOffset, popUpVOffset);
				menuPopUp.show();
			}
			
		});
		
		//add menuHeader to menuCanvas
		menuCanvas.add(submissionsMenuLabel);
		
	}
	
	class DisplayCreateSubmissionClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.displayCreateSubmissionWidget();
			menuPopUp.hide();
		}
		
	}
	
	class DisplayViewAllSubmissionsClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.displayViewAllSubmissionsWidget();
			menuPopUp.hide();
		}
		
	}
	
	public void onDisplayStatusChanged(Integer before, Integer after) {
		if (after == SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY) {
			displayCanvas.clear();
			createSubmissionWidget.setUpNewSubmission(authorEmail);
			displayCanvas.add(createSubmissionWidgetCanvas);
		} else if (after == SubmissionManagementWidgetModel.DISPLAYING_VIEW_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(viewSubmissionWidgetCanvas);
		} else if (after == SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES) {
			displayCanvas.clear();
			viewAllSubmissionsWidget.loadSubmissionsByAuthorEmail(authorEmail);
			displayCanvas.add(viewAllSubmissionsWidgetCanvas);
		} else if (after == SubmissionManagementWidgetModel.DISPLAYING_EDIT_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(editSubmissionWidgetCanvas);
		}
		
	}	

}
