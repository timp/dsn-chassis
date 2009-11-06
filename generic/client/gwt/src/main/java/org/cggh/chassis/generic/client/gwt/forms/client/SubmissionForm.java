/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.forms.client;


import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;


/**
 * @author aliman
 *
 */
public class SubmissionForm 
	extends BaseForm<SubmissionEntry, SubmissionFeed, SubmissionFormRenderer> {
	
	
	
	
	private Log log;
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createAtomFactory()
	 */
	@Override
	protected AtomFactory<SubmissionEntry, SubmissionFeed> createAtomFactory() {
		return new SubmissionFactory();
	}
	
	
	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init();
		
		// nothing else to do

		log.leave();
	}
	

	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(SubmissionForm.class);
	}

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseForm#createRenderer()
	 */
	@Override
	protected SubmissionFormRenderer createRenderer() {
		return new SubmissionFormRenderer(this);
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
		log.enter("refreshStudies");
		this.renderer.refreshStudies();
		log.leave();
	}



	


}
