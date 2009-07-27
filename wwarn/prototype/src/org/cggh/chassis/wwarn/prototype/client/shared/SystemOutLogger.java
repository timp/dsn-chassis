/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;

/**
 * @author aliman
 *
 */
public class SystemOutLogger extends LoggerBase {

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#info(java.lang.String)
	 */
	public void info(String message) {
		System.out.println(contextualise(message));
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable exception) {
		System.out.println(contextualise(message));
		System.out.println(contextualise(exception.getLocalizedMessage()));
		exception.printStackTrace();
	}

}
