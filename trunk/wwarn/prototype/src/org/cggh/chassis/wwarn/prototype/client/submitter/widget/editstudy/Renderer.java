/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.editstudy;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.wwarn.prototype.client.forms.FormValidationException;
import org.cggh.chassis.wwarn.prototype.client.forms.StudyForm;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;
	private RootPanel rootPanel;
	private StudyForm form;

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
		
		form = new StudyForm();
		form.getSubmitButton().setText("update study");
		vp1.add(form.getFormPanel());

		form.getFormPanel().addSubmitHandler(new SubmitHandler() {

			public void onSubmit(SubmitEvent event) {
				try {
					StudyEntry study = form.createStudyEntry();
					controller.updateStudy(study);
					event.cancel();
				}
				catch (FormValidationException e) {
					Window.alert(e.getLocalizedMessage());
				}
			}
			
		});

	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

	public void onStudyEntryChanged(StudyEntry from, StudyEntry to) {
		form.populate(to);
	}

}
