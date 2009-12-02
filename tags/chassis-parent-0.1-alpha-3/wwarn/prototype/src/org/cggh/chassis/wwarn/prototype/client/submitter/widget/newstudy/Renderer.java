/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
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
	private Logger log;

	Renderer(Controller controller) {
		this.controller = controller;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Renderer.class.getName());
	}

	void render() {
		log.enter("render");
		
		rootPanel.clear();

		VerticalPanel vp1 = new VerticalPanel();
		vp1.setSpacing(0);
		vp1.setWidth("100%");
		rootPanel.add(vp1);
		
		vp1.add(new HTML("<h2>New Study</h2>"));
		vp1.add(new HTML("<p>Please enter some details about the study...</p>"));
		
		log.trace("create study form");
		final StudyForm form = new StudyForm();
		form.getSubmitButton().setText("create new study");
		vp1.add(form.getFormPanel());
		
		form.getFormPanel().addSubmitHandler(new SubmitHandler() {

			public void onSubmit(SubmitEvent event) {
				try {
					StudyEntry study = form.createStudyEntry();
					controller.createStudy(study);
					event.cancel();
				}
				catch (FormValidationException e) {
					Window.alert(e.getLocalizedMessage());
				}
			}
			
		});
		
		log.leave();
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

}
