/**
 * 
 */
package org.cggh.chassis.generic.log.client;

import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class LogFactory {

	
	
	// use gwt log by default
	public static Function<String,Log> create = GWTLog.create;
	
	
	
	@SuppressWarnings("unchecked")
	public static Log getLog(java.lang.Class clazz) {
		return getLog(clazz.getName());
	}
	
	
	
	public static Log getLog(String name) {
		return create.apply(name);
	}
	
	
	
}
