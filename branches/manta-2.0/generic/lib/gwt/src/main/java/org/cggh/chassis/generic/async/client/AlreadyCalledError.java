/**
 * 
 */
package org.cggh.chassis.generic.async.client;

/**
 * @author aliman
 *
 */
@SuppressWarnings("unchecked")
public class AlreadyCalledError extends Error {

	private static final long serialVersionUID = 1L;

	
	private Deferred called;

	public AlreadyCalledError(Deferred called) {
		super("Deferred has already been called.");
		this.called = called;
	}
	
	public Deferred getCalled() {
		return this.called;
	}
}
