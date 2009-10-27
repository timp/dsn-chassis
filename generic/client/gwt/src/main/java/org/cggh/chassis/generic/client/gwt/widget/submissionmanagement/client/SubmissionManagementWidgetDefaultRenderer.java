/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.edit.client.EditSubmissionWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidget;
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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidgetDefaultRenderer implements SubmissionManagementWidgetModelListener {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	//Expose view elements for testing purposes.
	final Label displayCreateSubmissionUI = new Label("Create Submission");
	final Label displayViewAllSubmissionsUI = new Label("View My Submissions");
	final Panel displayCanvas;
	final Panel menuCanvas;
	final DecoratedPopupPanel menuPopUp = new DecoratedPopupPanel(true);
	final DecoratedPopupPanel confirmLoseChangesPopup = new DecoratedPopupPanel(false);

	final private SubmissionManagementWidgetController controller;
	final private SubmissionManagementWidget owner;
	
	
	
	
	//child widgets made package private to allow parent widget to access them
	final CreateSubmissionWidget createSubmissionWidget; 
	final ViewSubmissionWidget viewSubmissionWidget;
	final ViewSubmissionsWidget viewAllSubmissionsWidget;
	final EditSubmissionWidget editSubmissionWidget;
	private MenuBar menu;
	final SubmissionDataFileWidgetAPI submissionDataFileWidget;

	
	
	
	/**
	 * @param submissionManagementWidget
	 * @param controller2
	 */
	public SubmissionManagementWidgetDefaultRenderer(
			SubmissionManagementWidget owner, 
			SubmissionManagementWidgetController controller) {
		
		this.owner = owner;
		this.controller = controller;
		this.menuCanvas = new FlowPanel();
		this.displayCanvas = new FlowPanel();

		//TODO Remove use of canvas paramter constructors 
		//create child widgets
		this.viewSubmissionWidget = new ViewSubmissionWidget();
		this.createSubmissionWidget = new CreateSubmissionWidget();
		this.viewAllSubmissionsWidget = new ViewSubmissionsWidget();
		this.editSubmissionWidget = new EditSubmissionWidget();
		submissionDataFileWidget = new SubmissionDataFileWidget(ChassisUser.getCurrentUserEmail());
		
		//initialise view
		initMenu();

	}




	private void initMenu() {
		
		log.enter("initMenu");
		
		menu = new MenuBar(true);
		
		log.debug("construct new submission menu item");

		Command newSubmissionCommand = new Command() { 
			public void execute() { 
				log.enter("[anon Command] :: execute");
				
				log.debug("call displayCreateSubmissionWidget on controller");
				controller.displayCreateSubmissionWidget();
				
				log.debug("fire menu action on owner");
				owner.fireOnSubmissionManagementMenuAction();
				
				log.leave();
			} 
		};

		MenuItem newSubmissionMenuItem = new MenuItem("new data submission", newSubmissionCommand );
		menu.addItem(newSubmissionMenuItem);
		
		log.debug("construct my submissions menu item");
		
		Command viewSubmissionsCommand = new Command() { 
			public void execute() { 
				log.enter("[anon Command] :: execute");
				
				log.debug("call displayViewAllSubmissionsWidget on controller");
				controller.displayViewAllSubmissionsWidget(); 
				
				log.debug("fire menu action on owner");
				owner.fireOnSubmissionManagementMenuAction();
				
				log.leave();
			} 
		};

		MenuItem viewSubmissionsMenuItem = new MenuItem("my data submissions", viewSubmissionsCommand );
		menu.addItem(viewSubmissionsMenuItem);
		
		menuCanvas.add(menu); // only needed for legacy
		
		log.leave();		
	}
	
	
	
	
	public void onDisplayStatusChanged(Integer before, Integer after) {
		
		if (after == SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION) {
			
			displayCanvas.clear();

			// TODO
//			createSubmissionWidget.setUpNewSubmission(ChassisUser.getCurrentUserEmail());

			displayCanvas.add(createSubmissionWidget);
			createSubmissionWidget.render();
			
		} else if (after == SubmissionManagementWidgetModel.DISPLAYING_VIEW_SUBMISSION) {
			
			displayCanvas.clear();
			displayCanvas.add(viewSubmissionWidget);
			
		} else if (after == SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS) {
			
			displayCanvas.clear();
			viewAllSubmissionsWidget.loadSubmissionsByAuthorEmail(ChassisUser.getCurrentUserEmail());
			displayCanvas.add(viewAllSubmissionsWidget);
			
		} else if (after == SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION) {
			
			displayCanvas.clear();
			displayCanvas.add(editSubmissionWidget);
			editSubmissionWidget.render();
			
		} else if (after == SubmissionManagementWidgetModel.DISPLAYING_SUBMISSION_DATA_FILE_UPLOAD_WIDGET) {
			
			displayCanvas.clear();
			displayCanvas.add((SubmissionDataFileWidget)submissionDataFileWidget);
			
		}
		
	}

	
	
	
	public void userMightLoseChanges(final Integer userRequestedView) {
		
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
				
				if (userRequestedView == SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION) {
					confirmLoseChangesPopup.hide();
					controller.displayCreateSubmissionWidget(true);
				} else if (userRequestedView == SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS) {
					confirmLoseChangesPopup.hide();
					controller.displayViewAllSubmissionsWidget(true);
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
	public Widget getCanvas() {
		return this.displayCanvas;
	}




	/**
	 * @return
	 */
	public MenuBar getMenu() {
		return this.menu;
	}
	
	
	

}
