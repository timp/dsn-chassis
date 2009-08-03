/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	private TextBox piNameInput;
	private TextBox piEmailInput;
	private TextBox startYearInput;
	private TextBox endYearInput;

	Renderer(Controller controller) {
		this.controller = controller;
	}

	void render() {
		rootPanel.clear();

		VerticalPanel vp1 = new VerticalPanel();
		vp1.setSpacing(0);
		vp1.setWidth("100%");
		rootPanel.add(vp1);
		
		vp1.add(new HTML("<h2>New Study</h2>"));
		vp1.add(new HTML("<p>Please enter some details about the study...</p>"));
		
		vp1.add(new HTML("<hr/><h3>1. Study Title</h3>"));
		vp1.add(new HTML("<p>Provide a title for the study..."));
		
		titleInput = new TextBox();
		titleInput.setWidth("40em");
		vp1.add(titleInput);
		
		vp1.add(new HTML("<p><em>E.g., @@TODO</em></p>"));
		
		vp1.add(new HTML("<hr/><h3>2. Summary</h3>"));
		vp1.add(new HTML("<p>Provide a short textual summary of the study..."));
		
		summaryInput = new TextArea();
		summaryInput.setSize("40em", "10em");
		vp1.add(summaryInput);

		vp1.add(new HTML("<p><em>E.g., @@TODO</em></p>"));

		vp1.add(new HTML("<hr/><h3>3. Principal Investigator</h3>"));
		vp1.add(new HTML("<p>Who was the PI?</p>"));
		
		vp1.add(new HTML("<h4>3.1. PI Full Name</h4>"));
		piNameInput = new TextBox();
		piNameInput.setWidth("40em");
		vp1.add(piNameInput);
		
		vp1.add(new HTML("<h4>3.2. PI Email Address</h4>"));
		piEmailInput = new TextBox();
		piEmailInput.setWidth("40em");
		vp1.add(piEmailInput);
		
		
		vp1.add(new HTML("<hr/><h3>4. Start and End</h3>"));
		vp1.add(new HTML("<p>In what years did the study start and end?</p>"));
		
		vp1.add(new HTML("<h4>4.1. Start Year</h4>"));
		
		startYearInput = new TextBox();
		startYearInput.setWidth("4em");
		vp1.add(startYearInput);
		
		vp1.add(new HTML("<h4>4.2. End Year</h4>"));
		
		endYearInput = new TextBox();
		endYearInput.setWidth("4em");
		vp1.add(endYearInput);
		
		vp1.add(new HTML("<hr/>"));
		
		Button submitButton = new Button("create new study");
		vp1.add(submitButton);
		
		submitButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				String title = titleInput.getValue();
				String summary = summaryInput.getValue();
				String piName = piNameInput.getValue();
				String piEmail = piEmailInput.getValue();
				String startYear = startYearInput.getValue();
				String endYear = endYearInput.getValue();
				try {

					StudyEntry study = new StudyEntry();
					study.setTitle(title);
					study.setSummary(summary);
					study.setStartYear(startYear);
					study.setEndYear(endYear);
					// TODO PI name and email
					
					controller.createStudy(study);
					
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

}
