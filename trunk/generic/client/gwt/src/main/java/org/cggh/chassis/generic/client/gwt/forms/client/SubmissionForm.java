/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.forms.client;

//import org.cggh.chassis.generic.atom.submission.client.form.SubmissionEntryForm;
//import org.cggh.chassis.generic.atom.submission.client.form.SubmissionEntryValidator;
//import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
//import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
//import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
//import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.rewrite.client.AtomFactory;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisResources;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.data.client.DataFileForm;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class SubmissionForm extends BaseForm<SubmissionEntry, SubmissionFeed> {
	
	
	
	
	private Log log = LogFactory.getLog(SubmissionForm.class);

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createAtomFactory()
	 */
	@Override
	protected AtomFactory<SubmissionEntry, SubmissionFeed> createAtomFactory() {
		return new SubmissionFactory();
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createRenderer()
	 */
	@Override
	protected BaseFormRenderer<SubmissionEntry> createRenderer() {
		return new SubmissionFormRenderer();
	}
	
	
	
	
	
	public static class Resources extends org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources {
		
		public static final String HEADINGMODULES = "HEADINGMODULES";
		public static final String HEADINGSTUDIES = "HEADINGSTUDIES";
		public static final String QUESTIONLABELMODULES = "QUESTIONLABELMODULES";
		public static final String QUESTIONLABELSTUDIES = "QUESTIONLABELSTUDIES";

		public Resources() {
			super(SubmissionForm.class.getName());
		}
		
	}



	
	/**
	 * 
	 */
	public void refreshStudies() {
		((SubmissionFormRenderer)this.renderer).refreshStudies();
	}



	
	
	
//	
//	private SubmissionFormRenderer renderer;
//	private Panel canvas = new FlowPanel();
//
//
//
//
//	public SubmissionForm() {
//		super();
//		this.initWidget(this.canvas);
//		this.initModel();
//		this.validator = new Validator();
//		this.renderer = new SubmissionFormRenderer(this.canvas, this.model);
//	}
//	
//	
//	
//	
//	private void initModel() {
//
//		SubmissionFactory factory = new SubmissionFactoryImpl();
//		this.model = factory.createSubmissionEntry();
//
//		AtomAuthor author = factory.createAuthor();
//		author.setEmail(ChassisUser.getCurrentUserEmail());
//		this.model.addAuthor(author);
//		
//	}
//	
//	
//	
//	
//	public void setModel(SubmissionEntry model) {
//		log.enter("setModel");
//		this.model = model;
//		this.renderer.bind(model);
//		this.render();
//		log.leave();
//	}
//	
//	
//	
//	
//	public void render() {
//		this.renderer.render();
//	}
//	
//	
//	
//	
//	private class Validator extends SubmissionEntryValidator {
//		
//		private Validator() {
//			super();
//			this.titleRequired = true;
//			this.summaryRequired = true;
//			this.minModules = 1;
//			this.minStudyLinks = 1;
//		}
//		
//	}
//	
//	
//	
//	public static class Resources {
//	
//		public static final String HEADINGTITLEANDSUMMARY = "HEADINGTITLEANDSUMMARY";
//		public static final String HEADINGMODULES = "HEADINGMODULES";
//		public static final String HEADINGSTUDIES = "HEADINGSTUDIES";
//		public static final String QUESTIONLABELTITLE = "QUESTIONLABELTITLE";
//		public static final String QUESTIONLABELSUMMARY = "QUESTIONLABELSUMMARY";
//		public static final String QUESTIONLABELMODULES = "QUESTIONLABELMODULES";
//		public static final String QUESTIONLABELSTUDIES = "QUESTIONLABELSTUDIES";
//		
//		public static String get(String key) {
//			return ChassisResources.get(SubmissionForm.class.getName(), ChassisUser.getLang(), key);
//		}
//		
//	}
//
//
//
//	
//	
//	

}
