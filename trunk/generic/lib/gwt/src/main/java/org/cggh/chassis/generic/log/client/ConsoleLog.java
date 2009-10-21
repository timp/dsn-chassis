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

	

	public native void doDebug(String contextualisedMessage) /*-{
		console.debug(contextualisedMessage);
	}-*/;
	
	
	
	public native void doDebug(String contextualisedMessage, Throwable exception) /*-{
		console.debug(contextualisedMessage, exception);
	}-*/;



	public native void doInfo(String contextualisedMessage) /*-{
		console.info(contextualisedMessage);
	}-*/;



	public native void doInfo(String contextualisedMessage, Throwable exception) /*-{
		console.info(contextualisedMessage, exception);
	}-*/;



	public void debug(String message) {
		doDebug(contextualise(message));
	}



	public void debug(String message, Throwable exception) {
		doDebug(contextualise(message), exception);
	}



	public void info(String message) {
		doInfo(contextualise(message));
	}



	public void info(String message, Throwable exception) {
		doInfo(contextualise(message), exception);
	}



}
