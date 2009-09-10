/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

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
public class EditStudyWidgetDefaultRenderer implements EditStudyWidgetModelListener {


	//Expose view elements for testing purposes.
	final TextBoxBase titleUI = new TextBox();
	final TextBoxBase summaryUI = new TextArea();
	final CheckBox acceptClinicalDataUI = new CheckBox("Clinical");
	final CheckBox acceptMolecularDataUI = new CheckBox("Molecular");
	final CheckBox acceptInVitroDataUI = new CheckBox("In Vitro");
	final CheckBox acceptPharmacologyDataUI = new CheckBox("Pharmacology");
	final Button cancelEditStudyUI = new Button("Cancel", new CancelStudyClickHandler());
	final Button updateStudyUI = new Button("Update Study", new UpdateStudyClickHandler());
	
	final private Panel canvas;
	private EditStudyWidgetController controller;
	private Boolean isFormComplete = false;

	public EditStudyWidgetDefaultRenderer(Panel canvas, EditStudyWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
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
		acceptClinicalDataUI.addValueChangeHandler(new AcceptClinicalDataChangeHandler());
		editStudyForm.setWidget(++rowNumber, 0, acceptClinicalDataUI);
		acceptMolecularDataUI.addValueChangeHandler(new AcceptMolecularDataChangeHandler());
		editStudyForm.setWidget(++rowNumber, 0, acceptMolecularDataUI);
		acceptInVitroDataUI.addValueChangeHandler(new AcceptInVitroDataChangeHandler());
		editStudyForm.setWidget(++rowNumber, 0, acceptInVitroDataUI);
		acceptPharmacologyDataUI.addValueChangeHandler(new AcceptPharmacologyDataChangeHandler());
		editStudyForm.setWidget(++rowNumber, 0, acceptPharmacologyDataUI);
		
		editStudyForm.setWidget(++rowNumber, 0, updateStudyUI);
		editStudyForm.setWidget(rowNumber, 1, cancelEditStudyUI);
		
		canvas.add(editStudyForm);

		
	}

	public void onFormCompleted(Boolean isFormComplete) {
		this.isFormComplete = isFormComplete;
	}

	public void onModulesChanged(Boolean isValid) {
		// TODO Can implement user validation here
	}

	public void onStatusChanged(Integer before, Integer after) {
		// TODO Can implement user validation here
	}

	public void onSummaryChanged(Boolean isValid) {
		// TODO Can implement user validation here
	}

	public void onTitleChanged(Boolean isValid) {
		// TODO Can implement user validation here
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
	
	class AcceptClinicalDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptClinicalData(acceptClinicalDataUI.getValue());			
		}
		
	}
	
	class AcceptMolecularDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptMolecularData(acceptMolecularDataUI.getValue());			
		}
		
	}
	
	class AcceptInVitroDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptInVitroData(acceptInVitroDataUI.getValue());			
		}
		
	}
	
	class AcceptPharmacologyDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptPharmacologyData(acceptPharmacologyDataUI.getValue());			
		}
		
	}
	
	class UpdateStudyClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			if (isFormComplete) {
				controller.putUpdatedStudy();
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
			controller.cancelEditStudy();
		}
	}

	public void setController(EditStudyWidgetController customController) {
		this.controller = customController;
	}

	public void onStudyEntryChanged(StudyEntry before, StudyEntry studyEntry) {
		
		//get studyEntry modules
		List<String> modules = studyEntry.getModules();
		
		//set up form, fire events to perform validation
		titleUI.setValue(studyEntry.getTitle(), true);
		summaryUI.setValue(studyEntry.getSummary(), true);
		acceptClinicalDataUI.setValue(modules.contains(EditStudyWidgetModel.MODULE_CLINICAL), true);
		acceptMolecularDataUI.setValue(modules.contains(EditStudyWidgetModel.MODULE_MOLECULAR), true);
		acceptInVitroDataUI.setValue(modules.contains(EditStudyWidgetModel.MODULE_IN_VITRO), true);
		acceptPharmacologyDataUI.setValue(modules.contains(EditStudyWidgetModel.MODULE_PHARMACOLOGY), true);
		
	}

}
