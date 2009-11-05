/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.forms.client;


import org.cggh.chassis.generic.atom.rewrite.client.AtomFactory;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionFeed;


/**
 * @author aliman
 *
 */
public class SubmissionForm 
	extends BaseForm<SubmissionEntry, SubmissionFeed, SubmissionFormRenderer> {
	
	
	
	
	
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
	protected SubmissionFormRenderer createRenderer() {
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
		this.renderer.refreshStudies();
	}



	


}
