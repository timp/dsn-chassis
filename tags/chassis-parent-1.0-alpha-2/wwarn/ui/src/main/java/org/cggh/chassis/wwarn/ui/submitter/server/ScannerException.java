package org.cggh.chassis.wwarn.ui.submitter.server;
/**
 * @author timp
 * @since 25-Jan-2010
 */ 
public class ScannerException extends Exception {

	private static final long serialVersionUID = -3414255013789644207L;

	public ScannerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScannerException(String message) {
		super(message);
	}
}
