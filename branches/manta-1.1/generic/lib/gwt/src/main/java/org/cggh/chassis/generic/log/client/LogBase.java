/**
 * 
 */
package org.cggh.chassis.generic.log.client;

import java.util.Stack;

/**
 * @author aliman
 *
 */
public abstract class LogBase implements Log {


	protected String name = null;
	protected String methodName = null;
	protected Stack<String> methodStack = new Stack<String>();
	
	/**
	 * @param name
	 */
	public LogBase(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String contextualise(String message) {
		if (methodName != null) {
			message = methodName + " :: " + message;
		}
		if (name != null) {
			message = name + " :: " + message;
		}
		return message;
	}
	
	public void enter(String methodName) {
		methodStack.push(methodName);
		this.methodName = methodName;
		this.debug("enter");
	}
	
	public void leave() {
		this.debug("leave");
		methodStack.pop();
		if (methodStack.size() > 0) {
			methodName = methodStack.peek();
		}
		else {
			methodName = null;
		}
	}

}
