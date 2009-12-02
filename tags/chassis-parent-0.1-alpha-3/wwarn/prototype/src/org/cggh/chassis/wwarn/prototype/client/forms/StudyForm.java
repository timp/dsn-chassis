/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.forms;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class StudyForm {

	private FormPanel formPanel;
	private TextBox titleInput;
	/**
	 * @return the formPanel
	 */
	public FormPanel getFormPanel() {
		return formPanel;
	}

	/**
	 * @return the titleInput
	 */
	public TextBox getTitleInput() {
		return titleInput;
	}

	/**
	 * @return the summaryInput
	 */
	public TextArea getSummaryInput() {
		return summaryInput;
	}

	/**
	 * @return the clinicalInput
	 */
	public CheckBox getClinicalInput() {
		return clinicalInput;
	}

	/**
	 * @return the invitroInput
	 */
	public CheckBox getInvitroInput() {
		return invitroInput;
	}

	/**
	 * @return the pharmacologyInput
	 */
	public CheckBox getPharmacologyInput() {
		return pharmacologyInput;
	}

	/**
	 * @return the molecularInput
	 */
	public CheckBox getMolecularInput() {
		return molecularInput;
	}

	/**
	 * @return the submitButton
	 */
	public Button getSubmitButton() {
		return submitButton;
	}


	private TextArea summaryInput;
	private CheckBox clinicalInput;
	private CheckBox invitroInput;
	private CheckBox pharmacologyInput;
	private CheckBox molecularInput;
	private Button submitButton;
	private VerticalPanel contentPanel;
	
	public StudyForm() {
		init();
	}

	private void init() {
		
		formPanel = new FormPanel();
		
		contentPanel = new VerticalPanel();
		formPanel.setWidget(contentPanel);
		
		contentPanel.add(new HTML("<h3>1. Title</h3>"));
		contentPanel.add(new HTML("<p>Provide a title for the study..."));
		
		titleInput = new TextBox();
		titleInput.setWidth("40em");
		contentPanel.add(titleInput);
		
		contentPanel.add(new HTML("<h3>2. Summary</h3>"));
		contentPanel.add(new HTML("<p>Provide a short textual summary of the study..."));
		
		summaryInput = new TextArea();
		summaryInput.setSize("40em", "10em");
		contentPanel.add(summaryInput);

		contentPanel.add(new HTML("<h3>3. Modules</h3>"));
		contentPanel.add(new HTML("<p>Select one or more WWARN modules to which the study is relevant...</p>"));
		
		clinicalInput = new CheckBox();
		invitroInput = new CheckBox();
		pharmacologyInput = new CheckBox();
		molecularInput = new CheckBox();

		Widget[][] widgets = { 
			{ new Label("clinical: "), clinicalInput } ,
			{ new Label("in vitro: "), invitroInput } ,
			{ new Label("pharmacology: "), pharmacologyInput } ,
			{ new Label("molecular: "), molecularInput }
		};

		Grid g = new Grid(4,2);
		contentPanel.add(g);
		
		for (int row=0; row<4; row++) {
			for (int col=0; col<2; col++) {
				g.setWidget(row, col, widgets[row][col]);
			}			
		}	
		
		submitButton = new Button("submit");
		
		submitButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		
		contentPanel.add(submitButton);
		
	}

	public StudyEntry createStudyEntry() throws FormValidationException {

		StudyEntry study = null;
		
		String title = titleInput.getValue();
		String summary = summaryInput.getValue();
		
		boolean cli = clinicalInput.getValue();
		boolean inv = invitroInput.getValue();
		boolean pha = pharmacologyInput.getValue();
		boolean mol = molecularInput.getValue();
		
		if (title.trim().equals("")) {
			throw new FormValidationException("A title must be provided.");
		}
		
		if (!cli && !inv && !pha && !mol) {
			throw new FormValidationException("At least one module must be selected.");
		}
		
		try {

			study = new StudyEntry();
			study.setTitle(title);
			study.setSummary(summary);
			study.setModules(cli, inv, pha, mol);
			
		}
		catch (AtomFormatException ex) {
			// TODO should never be thrown
		}
		
		return study;
	}

	public void populate(StudyEntry to) {
		titleInput.setValue(to.getTitle());
		summaryInput.setValue(to.getSummary());
		clinicalInput.setValue(to.getModule(ChassisNS.CLINICAL));
		invitroInput.setValue(to.getModule(ChassisNS.INVITRO));
		pharmacologyInput.setValue(to.getModule(ChassisNS.PHARMACOLOGY));
		molecularInput.setValue(to.getModule(ChassisNS.MOLECULAR));
	}
}
