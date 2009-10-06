/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author raok
 *
 */
class CreateStudyWidgetDefaultRenderer implements StudyModelListener {

	final private Panel canvas;
	private StudyControllerCreateAPI controller;
	private Boolean isFormComplete = false;
	private Map<String, String> modulesConfig;
	
	//Expose view elements for testing purposes.
	final TextBoxBase titleUI = new TextBox();
	final TextBoxBase summaryUI = new TextArea();
	final Button createStudyUI = new Button("Create Study", new CreateStudyClickHandler());
	final Button cancelCreateStudyUI = new Button("Cancel", new CancelStudyClickHandler());
	final Map<String, CheckBox> modulesUIHash = new HashMap<String, CheckBox>();
	

	public CreateStudyWidgetDefaultRenderer(Panel canvas, StudyControllerCreateAPI controller) {
		this.canvas = canvas;
		this.controller = controller;
		this.modulesConfig = ConfigurationBean.getModules();
		
		//initialise view
		initCanvas();
	}

	private void initCanvas() {

		//Prepare form
		FlexTable createStudyForm = new FlexTable();
		int rowNumber = -1;
		
		Label titleLabel = new Label("Enter title:");
		createStudyForm.setWidget(++rowNumber, 0, titleLabel);
		titleUI.addValueChangeHandler(new TitleChangeHandler());
		createStudyForm.setWidget(++rowNumber, 0, titleUI);
		
		Label summaryLabel = new Label("Enter summary:");
		createStudyForm.setWidget(++rowNumber, 0, summaryLabel);
		summaryUI.addValueChangeHandler(new SummaryChangeHandler());
		createStudyForm.setWidget(++rowNumber, 0, summaryUI);

		Label modulesLabel = new Label("Select modules (at least one must be selected):");
		createStudyForm.setWidget(++rowNumber, 0, modulesLabel);
		
		//Create as many modules checkboxes as required
		for (String moduleId : modulesConfig.keySet()) {
			
			String UILabel = modulesConfig.get(moduleId);
			CheckBox moduleUICheckBox = new CheckBox(UILabel);
			
			//add valueChangeHandler
			moduleUICheckBox.addValueChangeHandler(new ModulesUIValueChangeHandler());
			
			//add checkbox reference to HashMap
			modulesUIHash.put(moduleId, moduleUICheckBox);
			
			//add to GUI
			createStudyForm.setWidget(++rowNumber, 0, moduleUICheckBox);
			
		}
		
		
		createStudyForm.setWidget(++rowNumber, 0, createStudyUI);
		createStudyForm.setWidget(rowNumber, 1, cancelCreateStudyUI);
		
		canvas.add(createStudyForm);	
		
	}
	
	class TitleChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.updateTitle(titleUI.getValue());			
		}
		
	}
	
	class SummaryChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.updateSummary(summaryUI.getValue());			
		}
		
	}

	class ModulesUIValueChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			
			//get modules selected
			Set<String> modulesSelected = new HashSet<String>();
			
			for (String moduleId : modulesUIHash.keySet()) {
				
				CheckBox moduleUICheckBox = modulesUIHash.get(moduleId);
				
				if (moduleUICheckBox.getValue()) {
					modulesSelected.add(moduleId);
				}
				
			}
			
			//update model
			controller.updateModules(modulesSelected);
			
		}
		
	}
	
	
	class CreateStudyClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			if (isFormComplete) {
				controller.saveNewStudyEntry();
			} else {
				//TODO move to management widget?
				DecoratedPopupPanel errorPopUp = new DecoratedPopupPanel(true);
				errorPopUp.add(new Label("Form invalid."));
				errorPopUp.center();
				errorPopUp.show();
			}
		}
		
	}
	
	//TODO handle cancel button (with history?)
	class CancelStudyClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.cancelSaveOrUpdateStudyEntry();
		}
	}

	public void setController(StudyControllerCreateAPI controller) {
		this.controller = controller;
	}

	public void onStatusChanged(Integer before, Integer after) {
		// TODO Auto-generated method stub
	}
	
	public void onStudyEntryChanged(Boolean isValid) {
		this.isFormComplete = isValid;		
	}

	public void onTitleChanged(String before, String after, Boolean isValid) {
		
		//do not fire events, otherwise a probably dangerous feedback loop will be created!
		titleUI.setValue(after, false);
	}

	public void onSummaryChanged(String before, String after, Boolean isValid) {

		summaryUI.setValue(after, false);
	}

	public void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid) {
		
		for (String moduleId : modulesConfig.keySet()) {
			
			modulesUIHash.get(moduleId).setValue(after.contains(moduleId), false);
			
		}
		
	}
	
}
