package org.cggh.chassis.generic.async.client;

/**
 * A function has an apply method, which is applied to an input (the domain)
 * to return an output (the codomain). 
 * 
 * @author aliman
 *
 */
public interface Function<I,O> {

	public O apply(I in);
	
}
