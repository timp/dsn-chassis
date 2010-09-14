/**
 * 
 */
package org.cggh.chassis.generic.log.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Function;

/**
 * @author aliman
 *
 */
public class LogFactory {

	
	
	// use gwt log by default
	public static Function<String,Log> create = GWTLog.create;
	
	
	
	private static Set<String> hide = new HashSet<String>();
	private static Set<String> show = new HashSet<String>();
	
	
	
	@SuppressWarnings("unchecked")
	public static Log getLog(java.lang.Class clazz) {
		return getLog(clazz.getName());
	}
	
	
	
	public static Log getLog(String name) {
		return create.apply(name);
	}
	
	
	
	public static void hide(String pattern) {
		hide.add(genRegex(pattern));
	}
	
	
	
	
	public static void show(String pattern) {
		show.add(genRegex(pattern));
	}
	
	
	
	
	private static String genRegex(String pattern) {
		pattern = pattern.replaceAll("\\.", "\\."); // map "." to "\\." so dots aren't matched as any char
		pattern = pattern.replaceAll("\\*", ".*"); // map "*" to ".*" so wildcards are handled sensibly
		return pattern;
	}
	
	
	
	
	public static boolean visible(Log log) {
		boolean visible = true; // start off everything is visible by default
		
		for (String pattern : hide) {
			if (log.getName().matches(pattern)) visible = false; // then apply hide rules
		}
		
		for (String pattern : show) {
			if (log.getName().matches(pattern)) visible = true; // then apply show rules
		}
		
		return visible;
	}
}
