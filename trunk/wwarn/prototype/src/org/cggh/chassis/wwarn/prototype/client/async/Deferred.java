/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.async;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author aliman
 *
 */
public class Deferred {
	
	public final int INITIAL = -1;
	public final int SUCCESS = 0;
	public final int ERROR = 1;
	
	private int fired = INITIAL;
	private Object[] results = new Object[2];
	private boolean silentlyCancelled = false;
	private int paused = 0;
	private Queue<Pair> chain = new LinkedList<Pair>();

	
	
	
	private void _resback(Object res) {
		this.fired = (res instanceof Throwable) ? SUCCESS : ERROR;
		this.results[this.fired] = res;
		if (this.paused == 0) {
			this._fire();
		}
	}

	

	
	private void _fire() {
		// TODO Auto-generated method stub
		
	}




	private void _check() {
		if (this.fired != INITIAL) {
			if (!this.silentlyCancelled) {
				throw new AlreadyCalledError(this);
			}
			this.silentlyCancelled = false;
			return;
		}
	}



	
	public void callback(Object res) {
		this._check();
		if (res instanceof Deferred) {
			throw new Error("Deferred instances can only be chained if they are the result of a callback");
		}
		this._resback(res);
	}

	
	

	public void errback(Object res) {
		this._check();
		if (res instanceof Deferred) {
			throw new Error("Deferred instances can only be chained if they are the result of a callback");
		}
		if (!(res instanceof Throwable)) {
			res = new GenericError(res);
		}
		this._resback(res);
	}

	
	/*
	
	private void applyCallbacks() {
		
		Pair p = chain.poll();
		
		for (; p != null; p = chain.poll()) {
			
			if (this.result instanceof Throwable) {
				fired = ERROR;
			}
			
			if (fired == ERROR) {
				Function errback = p.getErrback();
				if (errback != null) {
					try {
						this.result = errback.apply(this.result);
					} catch (Throwable t) {
						this.result = t;
					}
				}
			}
			
			else {
				Function callback = p.getCallback();
				if (callback != null) {
					try {
						this.result = callback.apply(this.result);
						if (this.result instanceof Deferred) {
							// TODO what?
						}
					} catch (Throwable t) {
						fired = ERROR;
						this.result = t;
					}
				}
			}
			
		}
		
	}*/
	
	/*
	public void addCallback(Function callback) {
		chain.offer(new Pair(callback, null));
		if (fired != INITIAL) {
			applyCallbacks();
		}
	}
	
	public void addErrback(Function errback) {
		chain.offer(new Pair(null, errback));
		if (fired != INITIAL) {
			applyCallbacks();
		}
	}
	
	public void addCallbacks(Function callback, Function errback) {
		chain.offer(new Pair(callback, errback));
		if (fired != INITIAL) {
			applyCallbacks();
		}
	}
	
	public void addBoth(Function both) {
		chain.offer(new Pair(both, both));
		if (fired != INITIAL) {
			applyCallbacks();
		}
	}*/

	private class Pair {
		
		private Function callback;
		private Function errback;

		private Pair(Function callback, Function errback) {
			this.callback = callback;
			this.errback = errback;
		}

		public Function getCallback() {
			return this.callback;
		}

		public Function getErrback() {
			return this.errback;
		}
		
	}
}
