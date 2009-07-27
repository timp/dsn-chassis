/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;


import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
public class GWTLogger extends LoggerBase {


	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String)
	 */
	public void info(String message) {
		GWT.log(contextualise(message), null);
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable exception) {
		GWT.log(contextualise(message), exception);
	}



}
