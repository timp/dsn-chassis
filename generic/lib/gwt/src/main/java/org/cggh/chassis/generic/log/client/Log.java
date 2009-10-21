/**
 * 
 */
package org.cggh.chassis.generic.log.client;

/**
 * @author aliman
 *
 */
public interface Log {

	public void debug(String message);
	public void debug(String message, Throwable exception);
	public void info(String message);
	public void info(String message, Throwable exception);
	public void setName(String className);
	public void enter(String methodName);
	public void leave();
	public String contextualise(String message);
}
