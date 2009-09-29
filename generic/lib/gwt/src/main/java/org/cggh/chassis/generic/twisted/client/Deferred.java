/**
 * 
 */
package org.cggh.chassis.generic.twisted.client;

import java.util.LinkedList;
import java.util.Queue;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
@SuppressWarnings("unchecked")
public class Deferred<T> {
	
	public static final int INITIAL = -1;
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	
	private int fired = INITIAL;
	private Object[] results = new Object[2];
	private boolean silentlyCancelled = false;
	private int paused = 0;
	private Queue<Pair> chain = new LinkedList<Pair>();
	private Function canceller = null;
	private boolean chained = false;
	private boolean finalized = false;
	private Function finalizer;
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	public Deferred() {
	}
	
	
	
	public Deferred(Function canceller) {
		this.canceller = canceller;
	}
	
	
	
	public void setCanceller(Function canceller) {
		this.canceller = canceller;
	}
	
	
	
	public int getStatus() {
		return this.fired;
	}
	
	public int getPaused() {
		return this.paused;
	}
	
	public Object getSuccessResult() {
		return this.results[SUCCESS];
	}
	
	public Object getErrorResult() {
		return this.results[ERROR];
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
		log.trace("set fired to: "+this.fired);
		
		this.results[this.fired] = res;
		if (this.paused == 0) {
			log.trace("firing callback chain");
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
		log.trace("result: "+res);
		
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
	
	
	
	public <X> Deferred<X> adapt(final Function<T,X> adapter) {
		
		final Deferred<X> adapted = new Deferred<X>();
		
		Function<T,T> callback = new Function<T,T>() {

			public T apply(T in) {
				X out = adapter.apply(in);
				adapted.callback(out);
				return in;
			}
			
		};
		
		Function<Throwable,Throwable> errback = new Function<Throwable,Throwable>() {

			public Throwable apply(Throwable in) {
				adapted.errback(in);
				return in;
			}
			
		};
		
		this.addCallbacks(callback, errback);
		
		return adapted;
		
	}
	
	
	public Deferred<T> addErrback(Function<? extends Throwable,? extends Throwable> errback) {
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

		log.trace("iterate through queue");
		for (int i=0; chain.size() > 0 && this.paused == 0; i++) {
			
			log.trace("iteration: "+i);
			log.trace("result: "+res+"; fired: "+fired);

			log.trace("pick pair of head of queue");
			Pair pair = chain.poll();
			
			Function f = (fired == SUCCESS)? pair.getCallback() : pair.getErrback();

			if (f == null) {
				log.trace("function to apply is null, continuing");
				continue;
			}
			
            try {
            	
            	log.trace("apply function");
            	res = f.apply(res);
            	
            	//
            	// N.B. this line means that, if we are in ERROR state, and an 
            	// errback returns something other than a Throwable, then the 
            	// fired status will go back to SUCCESS.
            	// I.e. all errback functions should have schema <Throwable,Throwable>. 
            	//
            	// The alternative would be to switch from SUCCESS to ERROR if
            	// the result is a Throwable, but not to allow switching back.
            	//
            	fired = ((res instanceof Throwable) ? ERROR : SUCCESS); 

            	// handle a deferred result
    			if (res instanceof Deferred) {
            		log.trace("handle a deferred result");
    				defres = (Deferred) res;
            		cb = new Function() {
            			public Object apply(Object in) {
            				log.enter("anonymous chain Function :: apply");
            				log.trace("decrement paused");
                			self.paused--;
                			log.trace("chain result");
                			self._resback(in);
                			log.leave();
                			return null;
            			}
            		};
            		this.paused++;
    			}

            } catch (Throwable err) {
            	log.trace("caught throwable from function: "+err.getLocalizedMessage());
            	fired = ERROR;
            	res = err;
            }	
            
		}
		
		log.trace("left iteration loop");
		
        this.fired = fired;
        this.results[fired] = res;
        
        if (this.chain.size() == 0 && this.paused == 0 && this.finalizer != null) {
        	log.trace("finalizing");
        	this.finalized = true;
        	this.finalizer.apply(res);
        }
        
        if (cb != null && this.paused == 1) {
        	// this is for "tail recursion" in case the dependent deferred
        	// is already fired
        	log.trace("add callback to deferred result to chain together");
        	defres.addBoth(cb);
        	defres.chained = true;
        }
        
        log.trace("leaving; fired: "+fired+"; paused: "+paused);
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
