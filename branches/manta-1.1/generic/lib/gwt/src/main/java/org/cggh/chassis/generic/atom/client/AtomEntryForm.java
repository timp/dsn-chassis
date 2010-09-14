/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author aliman
 *
 */
public abstract class AtomEntryForm<E extends AtomEntry> extends Composite {

	
	
	
	protected E model;
	protected AtomEntryValidator<E> validator;
	
	
	

	public AtomEntryForm() {}

	
	
	
	public AtomEntryForm(E model) {
		this.model = model;
	}
	
	
	
	
	public void setModel(E model) {
		this.model = model;
	}
	
	
	
	
	public E getModel() {
		return this.model;
	}
	
	
	
	
	public void setValidator(AtomEntryValidator<E> validator) {
		this.validator = validator;
	}
	
	
	
	
	public ValidationReport validate() {
		ValidationReport report = new ValidationReport();
		if (validator != null && this.model != null) {
			validator.validate(report, this.model);
		}
		return report;
	}
	
	
	
}
