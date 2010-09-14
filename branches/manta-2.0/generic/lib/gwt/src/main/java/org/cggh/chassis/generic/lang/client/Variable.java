/**
 * 
 */
package org.cggh.chassis.generic.lang.client;

/**
 * @author aliman
 *
 */
public class Variable<T> {

	private T value;

	public Variable() {}
	
	public Variable(T value) {
		this.value = value;
	}
	
	public void set(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
