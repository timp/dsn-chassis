/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetAPI;

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
public class StudyManagementWidgetDefaultRenderer implements StudyManagementWidgetModelListener {

	//Expose view elements for testing purposes.
	final Label displayCreateStudyUI = new Label("Create Study");
	final Label displayViewAllStudiesUI = new Label("View All Studies");
	final Panel displayCanvas;
	final Panel menuCanvas;
	final DecoratedPopupPanel menuPopUp = new DecoratedPopupPanel(true);

	final private StudyManagementWidgetController controller;
	
	//child widgets made package private to allow parent widget to access them
	final CreateStudyWidgetAPI createStudyWidget; 
	final Panel createStudyWidgetCanvas = new SimplePanel();
	final ViewStudyWidgetAPI viewStudyWidget;
	final Panel viewStudyWidgetCanvas = new SimplePanel();	
	final ViewAllStudiesWidgetAPI viewAllStudiesWidget;
	final Panel viewAllStudiesWidgetCanvas = new SimplePanel();
	final EditStudyWidgetAPI editStudyWidget;
	final Panel editStudyWidgetCanvas = new SimplePanel();

	
	public StudyManagementWidgetDefaultRenderer(Panel menuCanvas, Panel displayCanvas,
												StudyManagementWidgetController controller,
												String feedURL, 
												AtomService service) {
		this.menuCanvas = menuCanvas;
		this.displayCanvas = displayCanvas;
		this.controller = controller;
		
		//create child widgets
		viewStudyWidget = new ViewStudyWidget(viewStudyWidgetCanvas, service);
		createStudyWidget = new CreateStudyWidget(createStudyWidgetCanvas, service, feedURL);
		viewAllStudiesWidget = new ViewAllStudiesWidget(viewAllStudiesWidgetCanvas, service, feedURL);
		editStudyWidget = new EditStudyWidget(editStudyWidgetCanvas, service, feedURL);
		
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
			viewAllStudiesWidget.loadStudies();
			displayCanvas.add(viewAllStudiesWidgetCanvas);
		} else if (after == StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY) {
			displayCanvas.clear();
			displayCanvas.add(editStudyWidgetCanvas);
		}
		
	}	

}
