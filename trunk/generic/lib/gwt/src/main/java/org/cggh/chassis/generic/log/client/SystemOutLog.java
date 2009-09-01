/**
 * 
 */
package org.cggh.chassis.generic.log.client;

import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class SystemOutLog extends LogBase {


	
	public static Function<String, Log> create = new Function<String, Log>() {

		public Log apply(String in) {
			return new SystemOutLog(in);
		}
		
	};
	
	
	
	public SystemOutLog(String name) {
		super(name);
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#info(java.lang.String)
	 */
	public void trace(String message) {
		System.out.println(contextualise(message));
	}
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	public void trace(String message, Throwable exception) {
		System.out.println(contextualise(message));
		System.out.println(contextualise(exception.getLocalizedMessage()));
		exception.printStackTrace();
	}

	
	
}
