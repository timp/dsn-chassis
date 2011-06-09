package org.cggh.chassis.manta.util;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * A good reference is
 * http://java.sun.com/developer/technicalArticles/Programming/jndi/index.html
 * 
 * @author iwright
 * 
 */
public class LookupJNDI {

	private static final Logger logger = Logger.getLogger(LookupJNDI.class);

	/**
	 * Env entries can be configured in web.xml or via context
	 * 
	 * @throws NamingException
	 */
	public static <T> T getEnvEntry(String key) throws NamingException {
		// obtain the application component's environment
		// naming context
		javax.naming.Context ctx = new javax.naming.InitialContext();
		javax.naming.Context env = (Context) ctx.lookup("java:comp/env");

		// obtain the greetings message
		// configured by the deployer
		@SuppressWarnings("unchecked")
		T ret = (T) env.lookup(key);

		if (logger.isDebugEnabled()) {
			if (ret == null) {
				logger.debug("Unable to find env entry:"
						+ key
						+ ". This should be defined in application context(JNDI) or web.xml");
			}
		}
		return ret;
	}
}
