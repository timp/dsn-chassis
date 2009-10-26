/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.form.submission.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

import com.google.gwt.user.client.ui.Composite;




/**
 * @author aliman
 *
 */
public class OldSubmissionForm extends Composite {
	
	
	
	private OldSubmissionFormRenderer renderer;
	private OldSubmissionFormModel model;
	private OldSubmissionFormController controller;

	
	
	
	public OldSubmissionForm() {
	
		this.model = new OldSubmissionFormModel();
		this.controller = new OldSubmissionFormController(this.model);
		this.renderer = new OldSubmissionFormRenderer(this.controller);
		this.model.addListener(this.renderer);
		this.initWidget(this.renderer.getCanvas());

	}
	
	
	
	
	public void render() {
		this.renderer.render();
	}
	
	
	
	
	
	public void setEntry(SubmissionEntry entry) {
		this.controller.setEntry(entry);
	}
	
	
	

}
