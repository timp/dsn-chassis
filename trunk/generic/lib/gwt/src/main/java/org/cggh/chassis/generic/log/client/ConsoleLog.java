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
public class ConsoleLog extends LogBase {

	

	public static Function<String, Log> create = new Function<String, Log>() {

		public Log apply(String in) {
			return new ConsoleLog(in);
		}
		
	};

	

	public ConsoleLog(String name) {
		super(name);
	}

	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String)
	 */
	public native void doTrace(String contextualisedMessage) /*-{
		console.debug(contextualisedMessage);
	}-*/;
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String, java.lang.Throwable)
	 */
	public native void doTrace(String contextualisedMessage, Throwable exception) /*-{
		console.debug(contextualisedMessage, exception);
	}-*/;



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#trace(java.lang.String)
	 */
	public void trace(String message) {
		doTrace(contextualise(message));
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#trace(java.lang.String, java.lang.Throwable)
	 */
	public void trace(String message, Throwable exception) {
		doTrace(contextualise(message), exception);
	}



}
