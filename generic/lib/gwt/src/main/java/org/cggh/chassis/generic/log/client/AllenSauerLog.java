/**
 * 
 */
package org.cggh.chassis.generic.log.client;


import org.cggh.chassis.generic.async.client.Function;

/**
 * @author aliman
 *
 */
public class AllenSauerLog extends LogBase {

	

	public static Function<String, Log> create = new Function<String, Log>() {

		public Log apply(String in) {
			return new AllenSauerLog(in);
		}
		
	};

	

	public AllenSauerLog(String name) {
		super(name);
	}

	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String)
	 */
	public void debug(String message) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.debug(contextualise(message));
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.debug(contextualise(message), exception);
		}		
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#info(java.lang.String)
	 */
	public void info(String message) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.info(contextualise(message));
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.info(contextualise(message), exception);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#error(java.lang.String)
	 */
	public void error(String message) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.error(contextualise(message));
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.error(contextualise(message), exception);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#warn(java.lang.String)
	 */
	public void warn(String message) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.warn(contextualise(message));
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			com.allen_sauer.gwt.log.client.Log.warn(contextualise(message), exception);
		}
	}



}
