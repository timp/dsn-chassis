/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidget;

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
public class StudyManagementWidgetDefaultRenderer implements StudyManagementWidgetModelListener, CreateStudyWidgetPubSubAPI {

	//Expose view elements for testing purposes.
	final Label displayCreateStudyUI = new Label("Create Study");
	final Label displayViewAllStudiesUI = new Label("View All Studies");
	final Panel displayCanvas;
	final Panel menuCanvas;
	final DecoratedPopupPanel menuPopUp = new DecoratedPopupPanel(true);

	final private StudyManagementWidgetController controller;
	
	//child widgets
	private CreateStudyWidget createStudyWidget; 
	final Panel createStudyWidgetCanvas = new SimplePanel();
	private ViewStudyWidget viewStudyWidget;
	final Panel viewStudyWidgetCanvas = new SimplePanel();	
	private ViewAllStudiesWidget viewAllStudiesWidget;
	final Panel viewAllStudiesWidgetCanvas = new SimplePanel();


	// TODO handle run time setting of feedURL
	private String feedURL = "http://example.com/studies";
	
	public StudyManagementWidgetDefaultRenderer(Panel menuCanvas, Panel displayCanvas,
												StudyManagementWidgetController controller, AtomService service) {
		this.menuCanvas = menuCanvas;
		this.displayCanvas = displayCanvas;
		this.controller = controller;
		
		//create child widgets
		viewStudyWidget = new ViewStudyWidget(viewStudyWidgetCanvas, service);
		createStudyWidget = new CreateStudyWidget(createStudyWidgetCanvas, service, feedURL);
		viewAllStudiesWidget = new ViewAllStudiesWidget(viewAllStudiesWidgetCanvas, service);
		
		//register this Widget as a listener to the CreateStudyController
		createStudyWidget.addCreateStudyWidgetListener(this);
		
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
			createStudyWidget.setUpNewStudy();
			displayCanvas.add(createStudyWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(viewStudyWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES) {
			displayCanvas.clear();
			viewAllStudiesWidget.loadStudiesByFeedURL(feedURL);
			displayCanvas.add(viewAllStudiesWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY) {
			displayCanvas.clear();
		}
		
	}

	public void onNewStudyCreated(StudyEntry newStudyEntry) {
		viewStudyWidget.loadStudyEntry(newStudyEntry);
		controller.displayViewStudyWidget();
	}
	

}
