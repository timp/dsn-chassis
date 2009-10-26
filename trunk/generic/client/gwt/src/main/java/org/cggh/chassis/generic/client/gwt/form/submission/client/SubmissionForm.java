/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.form.submission.client;

import org.cggh.chassis.generic.atom.submission.client.form.SubmissionEntryForm;
import org.cggh.chassis.generic.atom.submission.client.form.SubmissionEntryValidator;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class SubmissionForm extends SubmissionEntryForm {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private SubmissionFormRenderer renderer;
	private Panel canvas = new FlowPanel();




	public SubmissionForm() {
		super();
		this.initWidget(this.canvas);
		this.initModel();
		this.validator = new Validator();
		this.renderer = new SubmissionFormRenderer(this.canvas, this.model);
	}
	
	
	
	
	private void initModel() {

		SubmissionFactory factory = new SubmissionFactoryImpl();
		this.model = factory.createSubmissionEntry();

		AtomAuthor author = factory.createAuthor();
		author.setEmail(ChassisUser.getCurrentUserEmail());
		this.model.addAuthor(author);
		
	}
	
	
	
	
	public void setModel(SubmissionEntry model) {
		this.model = model;
		this.render();
	}
	
	
	
	
	public void render() {
		this.renderer.render();
	}
	
	
	
	
	private class Validator extends SubmissionEntryValidator {
		
		private Validator() {
			super();
			this.titleRequired = true;
			this.summaryRequired = true;
			this.minModules = 1;
			this.minStudyLinks = 1;
		}
		
	}
	
	
	
	

}
