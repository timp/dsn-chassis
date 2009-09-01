/**
 * 
 */
package org.cggh.chassis.generic.log.client;

/**
 * @author aliman
 *
 */
public interface Log {

	public void trace(String message);
	public void trace(String message, Throwable exception);
	public void setName(String className);
	public void enter(String methodName);
	public void leave();
	public String contextualise(String message);
}
