/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile2.client;

import org.cggh.chassis.generic.atom.rewrite.client.AtomEntryForm;

/**
 * @author aliman
 *
 */
public abstract class DataFileEntryForm extends AtomEntryForm<DataFileEntry> {

	
	
	
	public DataFileEntryForm() {}

	
	
	
	public DataFileEntryForm(DataFileEntry entry) {
		super(entry);
	}
	
	
	
	
	public void setValidator(DataFileEntryValidator validator) {
		this.validator = validator;
	}
	
	
	
	
}
