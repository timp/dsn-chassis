/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.form;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.form.AtomEntryForm;

/**
 * @author aliman
 *
 */
public abstract class SubmissionEntryForm extends AtomEntryForm<SubmissionEntry> {

	
	
	
	public SubmissionEntryForm() {}

	
	
	
	public SubmissionEntryForm(SubmissionEntry entry) {
		super(entry);
	}
	
	
	
	
	public void setValidator(SubmissionEntryValidator validator) {
		this.validator = validator;
	}
	
	
	
	

	
	
	
}
