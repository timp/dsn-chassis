/**
 * 
 */
package org.cggh.chassis.generic.twisted.client;

/**
 * @author aliman
 *
 */
public interface Function<I,O> {

	public O apply(I in);
	
}
