/**
 * 
 */
package org.cggh.chassis.generic.log.client;


import org.cggh.chassis.generic.twisted.client.Function;

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

	
	
	public void debug(String message) {
		doDebug(contextualise(message));
	}



	public void debug(String message, Throwable exception) {
		doDebug(contextualise(message), exception);
	}






	public native void doInfo(String contextualisedMessage) /*-{
		console.info(contextualisedMessage);
	}-*/;



	public native void doInfo(String contextualisedMessage, Throwable exception) /*-{
		console.info(contextualisedMessage, exception);
	}-*/;





	public void info(String message) {
		doInfo(contextualise(message));
	}



	public void info(String message, Throwable exception) {
		doInfo(contextualise(message), exception);
	}


	
	
	
	
	
	



	public native void doWarn(String contextualisedMessage) /*-{
		console.warn(contextualisedMessage);
	}-*/;



	public native void doWarn(String contextualisedMessage, Throwable exception) /*-{
		console.warn(contextualisedMessage, exception);
	}-*/;





	public void warn(String message) {
		doWarn(contextualise(message));
	}



	public void warn(String message, Throwable exception) {
		doWarn(contextualise(message), exception);
	}

	
	
	
	
	
	
	



	public native void doError(String contextualisedMessage) /*-{
		console.error(contextualisedMessage);
	}-*/;



	public native void doError(String contextualisedMessage, Throwable exception) /*-{
		console.error(contextualisedMessage, exception);
	}-*/;





	public void error(String message) {
		doError(contextualise(message));
	}



	public void error(String message, Throwable exception) {
		doError(contextualise(message), exception);
	}


}
