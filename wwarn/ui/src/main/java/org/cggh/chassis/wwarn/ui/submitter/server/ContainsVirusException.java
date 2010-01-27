package org.cggh.chassis.wwarn.ui.submitter.server;

public class ContainsVirusException extends ScannerException {

	private static final long serialVersionUID = 431170477888774174L;

	public ContainsVirusException(String message) {
		super(message);
	}

	public ContainsVirusException(String message, Exception e) {
		super(message, e);
	}


}
