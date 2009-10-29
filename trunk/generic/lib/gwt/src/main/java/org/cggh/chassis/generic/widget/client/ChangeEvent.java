/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

/**
 * @author aliman
 *
 */
public class ChangeEvent<T> {

	private T before;
	
	/**
	 * @return the before
	 */
	public T getBefore() {
		return before;
	}

	/**
	 * @return the after
	 */
	public T getAfter() {
		return after;
	}

	private T after;
	
	public ChangeEvent(T before, T after) {
		this.before = before;
		this.after = after;
	}
	
	
}
