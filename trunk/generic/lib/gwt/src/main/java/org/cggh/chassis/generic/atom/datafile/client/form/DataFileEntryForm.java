/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile.client.form;

import org.cggh.chassis.generic.atom.vanilla.client.form.AtomEntryForm;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;

/**
 * @author raok
 *
 */
public class DataFileEntryForm extends AtomEntryForm<AtomEntry> {

	public DataFileEntryForm() {}
	
	public DataFileEntryForm(AtomEntry entry) {
		super(entry);
	}
	
	public void setValidator(DataFileEntryValidator validator) {
		this.validator = validator;
	}
	
}
