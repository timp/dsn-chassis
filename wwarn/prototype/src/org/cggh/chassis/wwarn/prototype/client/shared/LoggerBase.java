/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;

/**
 * @author aliman
 *
 */
public abstract class LoggerBase implements Logger {


	protected String className = null;
	protected String methodName = null;
	
	public void setCurrentClass(String className) {
		this.className = className;
	}
	
	public void setCurrentMethod(String methodName) {
		this.methodName = methodName;
	}

	public String contextualise(String message) {
		if (methodName != null) {
			message = methodName + " :: " + message;
		}
		if (className != null) {
			message = className + " :: " + message;
		}
		return message;
	}

}
