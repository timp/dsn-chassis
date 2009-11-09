/**
 * 
 */
package org.cggh.chassis.generic.log.client;


import org.cggh.chassis.generic.async.client.Function;

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
	public void debug(String message) {
		if (LogFactory.visible(this)) {
			GWT.log("[DEBUG] "+contextualise(message), null);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			GWT.log("[DEBUG] "+contextualise(message), exception);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#info(java.lang.String)
	 */
	public void info(String message) {
		if (LogFactory.visible(this)) {
			GWT.log("[INFO] "+contextualise(message), null);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			GWT.log("[INFO] "+contextualise(message), exception);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#error(java.lang.String)
	 */
	public void error(String message) {
		if (LogFactory.visible(this)) {
			GWT.log("[ERROR] "+contextualise(message), null);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			GWT.log("[ERROR] "+contextualise(message), exception);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#warn(java.lang.String)
	 */
	public void warn(String message) {
		if (LogFactory.visible(this)) {
			GWT.log("[WARN] "+contextualise(message), null);
		}
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			GWT.log("[WARN] "+contextualise(message), exception);
		}
	}



}
