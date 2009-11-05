/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.form;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.form.AtomEntryForm;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

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
