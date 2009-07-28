/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;

/**
 * @author aliman
 *
 */
public interface Logger {

	public void info(String message);
	public void info(String message, Throwable exception);
	public void setCurrentClass(String className);
	public void enter(String methodName);
	public void leave();
	public String contextualise(String message);
}
