/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
public class ValidationReport {

	private List<ValidationError> errors = new ArrayList<ValidationError>();
	
	
	/**
	 * @param validationError
	 */
	public void add(ValidationError error) {
		this.errors.add(error);
	}
	
	
	
	public List<ValidationError> getErrors() {
		return this.errors;
	}
	

}
