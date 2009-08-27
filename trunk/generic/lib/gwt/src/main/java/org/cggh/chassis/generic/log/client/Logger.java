/**
 * 
 */
package org.cggh.chassis.generic.log.client;

/**
 * @author aliman
 *
 */
public interface Logger {

	public void trace(String message);
	public void trace(String message, Throwable exception);
	public void setCurrentClass(String className);
	public void enter(String methodName);
	public void leave();
	public String contextualise(String message);
}
