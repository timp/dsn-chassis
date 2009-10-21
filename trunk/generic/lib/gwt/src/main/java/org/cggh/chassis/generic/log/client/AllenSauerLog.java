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
		com.allen_sauer.gwt.log.client.Log.debug(contextualise(message));
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#log(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String message, Throwable exception) {
		com.allen_sauer.gwt.log.client.Log.debug(contextualise(message), exception);
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#info(java.lang.String)
	 */
	public void info(String message) {
		com.allen_sauer.gwt.log.client.Log.info(contextualise(message));
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.log.client.Log#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable exception) {
		com.allen_sauer.gwt.log.client.Log.info(contextualise(message), exception);
	}



}
