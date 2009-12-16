/**
 * @author timp
 * @since 15 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.review;

/**
 * Thrown when a non-singular store (eg a list) is being used to 
 * store a single value and more than one value is found. 
 */
public class NotSingularException extends RuntimeException {

	private static final long serialVersionUID = 4818726101646020109L;

	public NotSingularException(String message) {
		super(message);
	}

}
