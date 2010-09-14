/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.Collection;



/**
 * @author aliman
 *
 */
public abstract class Validator<T> {

	
	
	public abstract void validate(ValidationReport report, T t);
	
	
	
	
	
	protected void validateRequired(Boolean required, String value, String message, ValidationReport report) {

		if (required != null && required && nullOrEmpty(value)) {
			report.add(new ValidationError(message));
		}
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	protected void validateMinCardinality(Integer cardinality, Collection values, String message, ValidationReport report) {
		if (cardinality != null && values.size() < cardinality) {
			report.add(new ValidationError(message.replaceAll(N, cardinality.toString())));
		}
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	protected void validateMaxCardinality(Integer cardinality, Collection values, String message, ValidationReport report) {
		if (cardinality != null && values.size() > cardinality) {
			report.add(new ValidationError(message.replaceAll(N, cardinality.toString())));
		}
	}
	
	
	
	
	/**
	 * @param title
	 * @return
	 */
	protected boolean nullOrEmpty(String value) {
		return (value == null || value.equals(""));
	}
	
	
	
	
	public static final String N = "{n}";
	
	
	
	
}
