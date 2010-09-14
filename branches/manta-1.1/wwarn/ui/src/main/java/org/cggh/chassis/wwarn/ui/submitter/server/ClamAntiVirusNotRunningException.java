package org.cggh.chassis.wwarn.ui.submitter.server;

/**
 * @author timp
 * @since 27 Jan 2010
 */
public class ClamAntiVirusNotRunningException extends ScannerException {

	private static final long serialVersionUID = -522995831270887695L;

	public ClamAntiVirusNotRunningException(String message, Throwable cause) {
		super(message, cause);
	}

}
