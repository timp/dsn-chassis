/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author aliman
 *
 */
public abstract class LoggerBase implements Logger {


	protected String className = null;
	protected String methodName = null;
	protected Stack<String> methodStack = new Stack<String>();
	
	public void setCurrentClass(String className) {
		this.className = className;
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
	
	public void enter(String methodName) {
		methodStack.push(methodName);
		this.methodName = methodName;
		this.info("enter");
	}
	
	public void leave() {
		this.info("leave");
		methodStack.pop();
		if (methodStack.size() > 0) {
			methodName = methodStack.peek();
		}
		else {
			methodName = null;
		}
	}

}
