/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.form.submission.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

/**
 * @author aliman
 *
 */
public class OldSubmissionFormController {

	private OldSubmissionFormModel model;

	/**
	 * @param model
	 */
	public OldSubmissionFormController(OldSubmissionFormModel model) {
		this.model = model;
	}

	/**
	 * @param entry
	 */
	public void setEntry(SubmissionEntry entry) {
		this.model.setEntry(entry);
	}

	/**
	 * @param value
	 */
	public void setTitle(String value) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param value
	 */
	public void setSummary(String value) {
		// TODO Auto-generated method stub
		
	}

}
