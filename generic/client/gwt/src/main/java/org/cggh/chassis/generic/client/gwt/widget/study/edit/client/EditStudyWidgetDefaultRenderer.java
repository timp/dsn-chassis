/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

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
	
	final private Panel canvas;
	private StudyControllerEditAPI controller;
	private Boolean isFormComplete = false;
	private String feedURL;

	public EditStudyWidgetDefaultRenderer(Panel canvas, StudyControllerEditAPI controller, String feedURL) {
		this.canvas = canvas;
		this.controller = controller;
		this.feedURL = feedURL;
		
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
				controller.updateStudyEntry(feedURL);
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

	public void onAcceptClinicalDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptClinicalDataUI.setValue(after, false);
	}

	public void onAcceptInVitroDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptInVitroDataUI.setValue(after, false);
	}

	public void onAcceptMolecularDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptMolecularDataUI.setValue(after, false);
	}

	public void onAcceptPharmacologyDataChanged(Boolean before, Boolean after, Boolean isValid) {
		acceptPharmacologyDataUI.setValue(after, false);
	}

}
