/**
 * 
 */
package org.cggh.chassis.generic.log.client;

import org.cggh.chassis.generic.async.client.Function;


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
	public void debug(String message) {
		if (LogFactory.visible(this)) {
			System.out.println("[DEBUG] "+contextualise(message));
		}
	}
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			System.out.println("[DEBUG] "+contextualise(message));
			System.out.println("[DEBUG] "+contextualise(exception.getLocalizedMessage()));
			exception.printStackTrace();
		}
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#info(java.lang.String)
	 */
	public void info(String message) {
		if (LogFactory.visible(this)) {
			System.out.println("[INFO] "+contextualise(message));
		}
	}
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			System.out.println("[INFO] "+contextualise(message));
			System.out.println("[INFO] "+contextualise(exception.getLocalizedMessage()));
			exception.printStackTrace();
		}
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#warn(java.lang.String)
	 */
	public void warn(String message) {
		if (LogFactory.visible(this)) {
			System.out.println("[WARN] "+contextualise(message));
		}
	}
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.Logger#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			System.out.println("[WARN] "+contextualise(message));
			System.out.println("[WARN] "+contextualise(exception.getLocalizedMessage()));
			exception.printStackTrace();
		}
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.werror.prototype.client.shared.Logger#error(java.lang.String)
	 */
	public void error(String message) {
		if (LogFactory.visible(this)) {
			System.out.println("[ERROR] "+contextualise(message));
		}
	}
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.werror.prototype.client.shared.Logger#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String message, Throwable exception) {
		if (LogFactory.visible(this)) {
			System.out.println("[ERROR] "+contextualise(message));
			System.out.println("[ERROR] "+contextualise(exception.getLocalizedMessage()));
			exception.printStackTrace();
		}
	}

	
	
}
