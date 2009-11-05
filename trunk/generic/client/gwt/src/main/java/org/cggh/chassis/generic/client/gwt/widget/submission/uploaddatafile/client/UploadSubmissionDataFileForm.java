/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactoryImpl;

import org.cggh.chassis.generic.atom.datafile.client.form.DataFileEntryForm;
import org.cggh.chassis.generic.atom.datafile.client.form.DataFileEntryValidator;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class UploadSubmissionDataFileForm extends DataFileEntryForm {

	
	private Panel canvas = new FlowPanel();
	private DataFileFormRenderer renderer;

	public UploadSubmissionDataFileForm() {
		super();
		this.initWidget(this.canvas);
		this.initModel();
		this.validator = new Validator();
		this.renderer = new DataFileFormRenderer(this.canvas, this.model);
	}
	
	private void initModel() {
		
		AtomFactory factory = new AtomFactoryImpl();
		this.model = factory.createEntry();
		
	}

	public void render() {
		renderer.render();
	}
	
	
	private class Validator extends DataFileEntryValidator {
		
		private Validator() {
			super();
			this.titleRequired = true;
			this.summaryRequired = true;
		}
		
	}
	
	public DataFileFormRenderer getRenderer() {
		return renderer;
	}
	
}
