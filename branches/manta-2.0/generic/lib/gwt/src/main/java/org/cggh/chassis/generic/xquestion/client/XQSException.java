/**
 * @author timp
 * @since 11 Dec 2009
 */
package org.cggh.chassis.generic.xquestion.client;

public class XQSException extends RuntimeException {

	private static final long serialVersionUID = 1233148250805275022L;

	public XQSException() { }

	public XQSException(String message) {
		super(message);
	}

	public XQSException(String message, Throwable cause) {
		super(message, cause);
	}

}
