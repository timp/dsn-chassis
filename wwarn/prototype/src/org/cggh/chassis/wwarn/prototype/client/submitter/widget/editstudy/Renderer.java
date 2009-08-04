/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.editstudy;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;
	private RootPanel rootPanel;
	private TextBox titleInput;
	private TextArea summaryInput;
	private RadioButton clinicalButton;
	private RadioButton invitroButton;
	private RadioButton pharmacologyButton;
	private RadioButton molecularButton;

	Renderer(Controller controller) {
		this.controller = controller;
	}

	void render() {
		rootPanel.clear();

		VerticalPanel vp1 = new VerticalPanel();
		vp1.setSpacing(0);
		vp1.setWidth("100%");
		rootPanel.add(vp1);
		
		vp1.add(new HTML("<h2>Edit Study</h2>"));
		vp1.add(new HTML("<p>Please enter some details about the study...</p>"));
		
		vp1.add(new HTML("<h3>1. Title</h3>"));
		vp1.add(new HTML("<p>Provide a title for the study..."));
		
		titleInput = new TextBox();
		titleInput.setWidth("40em");
		vp1.add(titleInput);
		
		vp1.add(new HTML("<h3>2. Summary</h3>"));
		vp1.add(new HTML("<p>Provide a short textual summary of the study..."));
		
		summaryInput = new TextArea();
		summaryInput.setSize("40em", "10em");
		vp1.add(summaryInput);

		vp1.add(new HTML("<h3>3. Modules</h3>"));
		vp1.add(new HTML("<p>Select the WWARN modules to which the study is relevant...</p>"));
		
		HorizontalPanel hp1 = new HorizontalPanel();
		
		hp1.add(new Label("clinical: "));
		clinicalButton = new RadioButton("clinical");
		hp1.add(clinicalButton);

		hp1.add(new Label("in vitro: "));
		invitroButton = new RadioButton("invitro");
		hp1.add(invitroButton);

		hp1.add(new Label("pharmacology: "));
		pharmacologyButton = new RadioButton("pharmacology");
		hp1.add(pharmacologyButton);

		hp1.add(new Label("molecular: "));
		molecularButton = new RadioButton("molecular");
		hp1.add(molecularButton);
		
		vp1.add(hp1);
		
		vp1.add(new HTML("<hr/>"));
		
		Button submitButton = new Button("update study");
		vp1.add(submitButton);
		
		submitButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				String title = titleInput.getValue();
				String summary = summaryInput.getValue();
				
				boolean cli = clinicalButton.getValue();
				boolean inv = invitroButton.getValue();
				boolean pha = pharmacologyButton.getValue();
				boolean mol = molecularButton.getValue();
				
				if (title.trim().equals("")) {
					Window.alert("Please provide a title.");
					return;
				}
				
				if (!cli && !inv && !pha && !mol) {
					Window.alert("At least one module must be selected.");
					return;
				}
				
				try {

					StudyEntry study = new StudyEntry();
					study.setTitle(title);
					study.setSummary(summary);
					study.setModules(cli, inv, pha, mol);
					
					// TODO modules
					
					controller.updateStudy(study);
					
				}
				catch (AtomFormatException ex) {
					// TODO should never be thrown
				}
				
			}
		
		});
		
		// TODO more
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

	public void onStudyEntryChanged(StudyEntry from, StudyEntry to) {
		titleInput.setValue(to.getTitle());
		summaryInput.setValue(to.getSummary());
		clinicalButton.setValue(to.getModule(ChassisNS.CLINICAL));
		invitroButton.setValue(to.getModule(ChassisNS.INVITRO));
		pharmacologyButton.setValue(to.getModule(ChassisNS.PHARMACOLOGY));
		molecularButton.setValue(to.getModule(ChassisNS.MOLECULAR));
	}

}
