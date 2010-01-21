/**
 * 
 */
package org.cggh.chassis.generic.async.client;

/**
 * @author aliman
 *
 */
public interface Function<I,O> {

	public O apply(I in);
	
}
