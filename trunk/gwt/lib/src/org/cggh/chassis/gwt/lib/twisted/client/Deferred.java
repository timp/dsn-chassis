/**
 * 
 */
package org.cggh.chassis.gwt.lib.twisted.client;

import java.util.LinkedList;
import java.util.Queue;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;

/**
 * @author aliman
 *
 */
@SuppressWarnings("unchecked")
public class Deferred<T> {
	
	public final int INITIAL = -1;
	public final int SUCCESS = 0;
	public final int ERROR = 1;
	
	private int fired = INITIAL;
	private Object[] results = new Object[2];
	private boolean silentlyCancelled = false;
	private int paused = 0;
	private Queue<Pair> chain = new LinkedList<Pair>();
	private Function canceller = null;
	private boolean chained = false;
	private boolean finalized = false;
	private Function finalizer;
	
	private Logger log;

	
	
	
	public Deferred() {
		this.log = new GWTLogger();
		this.log.setCurrentClass(Deferred.class.getName());
	}
	
	
	
	public Deferred(Function canceller) {
		this.canceller  = canceller;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Deferred.class.getName());
	}
	
	
	
	
	public void cancel() {
        if (this.fired == INITIAL) {
        	if (this.canceller != null) {
        		this.canceller.apply(this);
        	} else {
        		this.silentlyCancelled = true;
        	}
        	if (this.fired == INITIAL) {
        		// TODO review, why do we do this?
        		this.errback(new CancelledError(this));
        	}
        } else if ((this.fired == SUCCESS) && (this.results[this.fired] instanceof Deferred)) {
        	Deferred d = (Deferred) this.results[this.fired];
        	d.cancel();
        }
	}
	
	
	
	
	private void _resback(Object res) {
		log.enter("_resback");
		
		this.fired = (res instanceof Throwable) ? ERROR : SUCCESS;
		this.results[this.fired] = res;
		if (this.paused == 0) {
			this._fire();
		}
		
		log.leave();
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



	
	public void callback(T res) {
		log.enter("callback");
		
		this._check();
		
		if (res instanceof Deferred) {
			throw new Error("Deferred instances can only be chained if they are the result of a callback");
		}
		
		this._resback(res);
		
		log.leave();
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

	

	
	public Deferred<T> addBoth(Function both) {
		return this.addCallbacks(both, both);
	}
	
	

	public Deferred<T> addCallback(Function callback) {
		return this.addCallbacks(callback, null);
	}
	
	
	public Deferred<T> addErrback(Function errback) {
		return this.addCallbacks(null, errback);
	}
	
	
	
	public Deferred<T> addCallbacks(Function callback, Function errback) {
        if (this.chained) {
        	throw new Error("Chained Deferreds can not be re-used");
        }
        if (this.finalized) {
        	throw new Error("Finalized Deferreds can not be re-used");
        }
        this.chain.offer(new Pair(callback, errback));
        if (this.fired >= 0) {
        	this._fire();
        }
        return this;		
	}
	
	
	
	
	public Deferred<T> setFinalizer(Function finalizer) {
        if (this.chained) {
        	throw new Error("Chained Deferreds can not be re-used");
        }
        if (this.finalized) {
        	throw new Error("Finalized Deferreds can not be re-used");
        }
        this.finalizer = finalizer;
        if (this.fired >= 0) {
        	this._fire();
        }
        return this;
	}

	
	
	
	
	/**
	 * Used internally to exhaust the callback sequence when a result is 
	 * available.
	 */
	private void _fire() {
		log.enter("_fire");

		Queue<Pair> chain = this.chain;
		int fired = this.fired;
		log.trace("fired: "+fired);
		
		Object res = this.results[fired];
		Function cb = null;
		final Deferred self = this;
		Deferred defres = null;
		if (res instanceof Deferred) {
			defres = (Deferred) res;
		}

		log.trace("iterate through queue");
		while (chain.size() > 0 && this.paused == 0 ) {
			
			log.trace("pick pair of head of queue");
			Pair pair = chain.poll();
			Function f = (this.fired == SUCCESS)? pair.getCallback() : pair.getErrback();
			if (f == null) {
				log.trace("function to apply is null, continuing");
				continue;
			}
			
            try {
            	res = f.apply(res);
            	fired = ((res instanceof Throwable) ? 1 : 0);
            	if (defres != null) {
            		cb = new Function() {
            			public Object apply(Object in) {
                			self.paused--;
                			self._resback(in);
                			return null;
            			}
            		};
            		this.paused++;
            	}
            } catch (Throwable err) {
            	fired = 1;
            	res = err;
            }	
            
		}
		
        this.fired = fired;
        this.results[fired] = res;
        
        if (this.chain.size() == 0 && this.paused == 0 && this.finalizer != null) {
        	this.finalized = true;
        	this.finalizer.apply(res);
        }
        
        if (cb != null && this.paused == 1) {
        	// this is for "tail recursion" in case the dependent deferred
        	// is already fired
        	defres.addBoth(cb);
        	defres.chained = true;
        }
        
        log.trace("leaving; fired: "+fired);
        log.leave();
	}

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
