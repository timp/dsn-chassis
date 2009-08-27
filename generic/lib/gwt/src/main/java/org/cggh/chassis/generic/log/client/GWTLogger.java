/**
 * 
 */
package org.cggh.chassis.generic.log.client;


import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
public class GWTLogger extends LoggerBase {


	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String)
	 */
	public void trace(String message) {
		GWT.log(contextualise(message), null);
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String, java.lang.Throwable)
	 */
	public void trace(String message, Throwable exception) {
		GWT.log(contextualise(message), exception);
	}



}
