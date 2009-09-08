/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
class CreateStudyWidgetDefaultRenderer implements CreateStudyWidgetModelListener {

	final private Panel canvas;
	private CreateStudyWidgetController controller;
	
	//Expose view elements for testing purposes.
	final TextBoxBase titleUI = new TextBox();
	final TextBoxBase summaryUI = new TextArea();
	final CheckBox acceptClinicalDataUI = new CheckBox("Clinical");
	final CheckBox acceptMolecularDataUI = new CheckBox("Molecular");
	final CheckBox acceptInVitroDataUI = new CheckBox("In Vitro");
	final CheckBox acceptPharmacologyDataUI = new CheckBox("Pharmacology");
	final Button createStudyUI = new Button("Create Study", new CreateStudyClickHandler());
	final Button cancelCreateStudyUI = new Button("Cancel", new CancelStudyClickHandler());
	

	public CreateStudyWidgetDefaultRenderer(Panel canvas, CreateStudyWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
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
		acceptClinicalDataUI.addValueChangeHandler(new AcceptClinicalDataChangeHandler());
		createStudyForm.setWidget(++rowNumber, 0, acceptClinicalDataUI);
		acceptMolecularDataUI.addValueChangeHandler(new AcceptMolecularDataChangeHandler());
		createStudyForm.setWidget(++rowNumber, 0, acceptMolecularDataUI);
		acceptInVitroDataUI.addValueChangeHandler(new AcceptInVitroDataChangeHandler());
		createStudyForm.setWidget(++rowNumber, 0, acceptInVitroDataUI);
		acceptPharmacologyDataUI.addValueChangeHandler(new AcceptPharmacologyDataChangeHandler());
		createStudyForm.setWidget(++rowNumber, 0, acceptPharmacologyDataUI);
		
		createStudyForm.setWidget(++rowNumber, 0, createStudyUI);
		createStudyForm.setWidget(rowNumber, 1, cancelCreateStudyUI);
		
		canvas.add(createStudyForm);	
		
	}

	public void onAcceptClinicalDataChanged(Boolean before, Boolean after) {
		// TODO Auto-generated method stub
		
	}

	public void onAcceptInVitroDataChanged(Boolean before, Boolean after) {
		// TODO Auto-generated method stub
		
	}

	public void onAcceptMolecularDataChanged(Boolean before, Boolean after) {
		// TODO Auto-generated method stub
		
	}

	public void onAcceptPharmacologyDataChanged(Boolean before, Boolean after) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(Integer before, Integer after) {
		// TODO Auto-generated method stub
		
	}

	public void onSummaryChanged(String before, String after) {
		// TODO Auto-generated method stub
		
	}

	public void onTitleChanged(String before, String after) {
		// TODO Auto-generated method stub
		
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
	
	class CreateStudyClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.saveNewStudy();
		}
		
	}
	
	//TODO handle cancel button (with history?)
	class CancelStudyClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.cancelCreateStudy();
		}
	}

	public void setController(CreateStudyWidgetController controller) {
		this.controller = controller;
	}
	
}
