/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.form;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author aliman
 *
 */
public abstract class AtomEntryForm<M extends AtomEntry> extends Composite {

	
	
	
	protected M model;
	protected AtomEntryValidator<M> validator;
	
	
	

	public AtomEntryForm() {}

	
	
	
	public AtomEntryForm(M model) {
		this.model = model;
	}
	
	
	
	
	public void setModel(M model) {
		this.model = model;
	}
	
	
	
	
	public M getModel() {
		return this.model;
	}
	
	
	
	
	public void setValidator(AtomEntryValidator<M> validator) {
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
