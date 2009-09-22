/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

import java.util.Set;

import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModelListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class CreateSubmissionWidgetDefaultRenderer implements SubmissionModelListener {

	private Panel canvas;
	private SubmissionControllerCreateAPI controller;
	private Boolean isFormComplete = false;
	
	//Expose view elements for testing purposes.
	final Panel createSubmissionFormPanel = new SimplePanel();
	final TextBoxBase titleUI = new TextBox();
	final TextBoxBase summaryUI = new TextArea();
	final CheckBox acceptClinicalDataUI = new CheckBox("Clinical");
	final CheckBox acceptMolecularDataUI = new CheckBox("Molecular");
	final CheckBox acceptInVitroDataUI = new CheckBox("In Vitro");
	final CheckBox acceptPharmacologyDataUI = new CheckBox("Pharmacology");
	final FlexTable studiesLinkedTableList = new FlexTable();
	final SimplePanel noStudiesAddedPanel = new SimplePanel();
	final Button showAddStudyLinkUI = new Button("Add a study");
	
	final ListBox studyLinkToAddUI = new ListBox();
	final Button addStudyLinkUI = new Button("Add study");
	
	final Button cancelCreateSubmissionUI = new Button("Cancel", new CancelCreateSubmissionUIClickHandler());
	final Button saveNewSubmissionEntryUI = new Button("Create Submission", new SaveNewSubmissionUIClickHandler());

	public CreateSubmissionWidgetDefaultRenderer(Panel canvas, SubmissionControllerCreateAPI controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		//initialise view
		initCanvas();
	}

	private void initCanvas() {
				
		//prepare form
		FlexTable createSubmissionForm = new FlexTable();
		int rowNumber = -1;
		
		Label titleLabel = new Label("Enter title:");
		createSubmissionForm.setWidget(++rowNumber, 0, titleLabel);
		titleUI.addValueChangeHandler(new TitleChangeHandler());
		createSubmissionForm.setWidget(++rowNumber, 0, titleUI);
		
		Label summaryLabel = new Label("Enter summary:"); 
		createSubmissionForm.setWidget(++rowNumber, 0, summaryLabel);
		summaryUI.addValueChangeHandler(new SummaryChangeHandler());
		createSubmissionForm.setWidget(++rowNumber, 0, summaryUI);

		Label modulesLabel = new Label("Select modules (at least one must be selected):");
		createSubmissionForm.setWidget(++rowNumber, 0, modulesLabel);
		acceptClinicalDataUI.addValueChangeHandler(new AcceptClinicalDataChangeHandler());
		createSubmissionForm.setWidget(++rowNumber, 0, acceptClinicalDataUI);
		acceptInVitroDataUI.addValueChangeHandler(new AcceptInVitroDataChangeHandler());
		createSubmissionForm.setWidget(++rowNumber, 0, acceptInVitroDataUI);
		acceptMolecularDataUI.addValueChangeHandler(new AcceptMolecularDataChangeHandler());
		createSubmissionForm.setWidget(++rowNumber, 0, acceptMolecularDataUI);
		acceptPharmacologyDataUI.addValueChangeHandler(new AcceptPharmacologyDataChangeHandler());
		createSubmissionForm.setWidget(++rowNumber, 0, acceptPharmacologyDataUI);
		
		
		//prepare panel to show list of studies added
		VerticalPanel studiesLinkedDisplayPanel = new VerticalPanel();
		
		studiesLinkedDisplayPanel.add(new Label("Studies Linked:"));
		studiesLinkedDisplayPanel.add(noStudiesAddedPanel);
		studiesLinkedDisplayPanel.add(studiesLinkedTableList);
		studiesLinkedDisplayPanel.add(showAddStudyLinkUI);		
		
		
		//prepare the studyLink chooser panel
		//TODO do not close unless cancel or valid study added.
		final DecoratedPopupPanel studyLinkChooserPopup = new DecoratedPopupPanel(true);
		VerticalPanel studyLinkChooserVP = new VerticalPanel();
		studyLinkChooserPopup.add(studyLinkChooserVP);
		studyLinkChooserVP.add(new Label("Choose a study for the drop down box and then click 'Add study'."));
		//TODO Add studies widget in form of a drop down
		studyLinkChooserVP.add(new Label("***Add studies widget in form of a drop down***"));
		
		//add clickHandler to display studyLinkChooserPopup
		showAddStudyLinkUI.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				studyLinkChooserPopup.center();
				studyLinkChooserPopup.show();
			}
		});
		
		//buttons panel
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.add(saveNewSubmissionEntryUI);
		buttonsPanel.add(cancelCreateSubmissionUI);
		
		//Place above into a holder panel, then add to canvas
		VerticalPanel mainVP = new VerticalPanel();
		mainVP.add(createSubmissionForm);
		mainVP.add(studiesLinkedDisplayPanel);
		mainVP.add(buttonsPanel);
		
		canvas.add(mainVP);
		
	}
	
	private class TitleChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.updateTitle(titleUI.getValue());
		}
		
	}
	
	private class SummaryChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.updateSummary(summaryUI.getValue());
		}
		
	}
	
	private class AcceptClinicalDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptClinicalData(acceptClinicalDataUI.getValue());			
		}
		
	}
	
	private class AcceptMolecularDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptMolecularData(acceptMolecularDataUI.getValue());			
		}
		
	}
	
	private class AcceptInVitroDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptInVitroData(acceptInVitroDataUI.getValue());			
		}
		
	}
	
	private class AcceptPharmacologyDataChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			controller.updateAcceptPharmacologyData(acceptPharmacologyDataUI.getValue());			
		}
		
	}
	
	private class CancelCreateSubmissionUIClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.cancelSaveOrUpdateSubmissionEntry();
		}
		
	}
	
	private class SaveNewSubmissionUIClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			
			if (isFormComplete){
				controller.saveNewSubmissionEntry();
			} else {
				//TODO move to management widget?
				DecoratedPopupPanel errorPopUp = new DecoratedPopupPanel(true);
				errorPopUp.add(new Label("Form invalid."));
				errorPopUp.center();
				errorPopUp.show();
			}
			
		}
		
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

	public void onStudyLinksChanged(Set<String> before, Set<String> after, Boolean isValid) {

		//show hide panel depending on if studies added
		noStudiesAddedPanel.setVisible(!isValid);
		
		
		//TODO add studies to studiesLinkedTableList 
		

	}

	public void onSubmissionEntryChanged(Boolean isValid) {
		this.isFormComplete = isValid;
	}

	public void onStatusChanged(Integer before, Integer after) {
		

	}

}
