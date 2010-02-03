/**
 * 
 */
package org.cggh.chassis.generic.async.client;

/**
 * @author aliman
 *
 */
@SuppressWarnings("unchecked")
public class CancelledError extends Error {

	private static final long serialVersionUID = 1L;
	private Deferred cancelled;

	public CancelledError(Deferred cancelled) {
		this.cancelled = cancelled;
	}
	
	public Deferred getCancelled() {
		return cancelled;
	}
}
