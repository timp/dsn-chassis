/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI;
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
public class EditStudyWidgetDefaultRenderer implements StudyModelListener {


	//Expose view elements for testing purposes.
	final TextBoxBase titleUI = new TextBox();
	final TextBoxBase summaryUI = new TextArea();
	final CheckBox acceptClinicalDataUI = new CheckBox("Clinical");
	final CheckBox acceptMolecularDataUI = new CheckBox("Molecular");
	final CheckBox acceptInVitroDataUI = new CheckBox("In Vitro");
	final CheckBox acceptPharmacologyDataUI = new CheckBox("Pharmacology");
	final Button cancelEditStudyUI = new Button("Cancel", new CancelStudyClickHandler());
	final Button updateStudyUI = new Button("Update Study", new UpdateStudyClickHandler());
	final Map<String, CheckBox> modulesUIHash = new HashMap<String, CheckBox>();
	
	final private Panel canvas;
	private StudyControllerEditAPI controller;
	private Boolean isFormComplete = false;
	private Map<String, String> modulesConfig;

	public EditStudyWidgetDefaultRenderer(Panel canvas, StudyControllerEditAPI controller) {
		this.canvas = canvas;
		this.controller = controller;
		this.modulesConfig = ConfigurationBean.getModules();
		
		initCanvas();
	}

	private void initCanvas() {

		//Prepare form
		FlexTable editStudyForm = new FlexTable();
		int rowNumber = -1;
		
		Label titleLabel = new Label("Edit title:");
		editStudyForm.setWidget(++rowNumber, 0, titleLabel);
		titleUI.addValueChangeHandler(new TitleChangeHandler());
		editStudyForm.setWidget(++rowNumber, 0, titleUI);
		
		Label summaryLabel = new Label("Edit summary:");
		editStudyForm.setWidget(++rowNumber, 0, summaryLabel);
		summaryUI.addValueChangeHandler(new SummaryChangeHandler());
		editStudyForm.setWidget(++rowNumber, 0, summaryUI);

		Label modulesLabel = new Label("Edit modules (at least one must be selected):");
		editStudyForm.setWidget(++rowNumber, 0, modulesLabel);

		//Create as many modules checkboxes as required
		for (String moduleId : modulesConfig.keySet()) {
			
			String UILabel = modulesConfig.get(moduleId);
			CheckBox moduleUICheckBox = new CheckBox(UILabel);
			
			//add valueChangeHandler
			moduleUICheckBox.addValueChangeHandler(new ModulesUIValueChangeHandler());
			
			//add checkbox reference to HashMap
			modulesUIHash.put(moduleId, moduleUICheckBox);
			
			//add to GUI
			editStudyForm.setWidget(++rowNumber, 0, moduleUICheckBox);
			
		}
		
		editStudyForm.setWidget(++rowNumber, 0, updateStudyUI);
		editStudyForm.setWidget(rowNumber, 1, cancelEditStudyUI);
		
		canvas.add(editStudyForm);
		
	}

	public void onStatusChanged(Integer before, Integer after) {
		// TODO Can handle error state here.
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
	
	class UpdateStudyClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			if (isFormComplete) {
				controller.updateStudyEntry();
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

	public void setController(StudyControllerEditAPI customController) {
		this.controller = customController;
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
