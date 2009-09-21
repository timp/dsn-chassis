/**
 * 
 */
package org.cggh.chassis.generic.log.client;


import org.cggh.chassis.generic.twisted.client.Function;

import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
public class GWTLog extends LogBase {

	

	public static Function<String, Log> create = new Function<String, Log>() {

		public Log apply(String in) {
			return new GWTLog(in);
		}
		
	};

	

	public GWTLog(String name) {
		super(name);
	}

	

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
