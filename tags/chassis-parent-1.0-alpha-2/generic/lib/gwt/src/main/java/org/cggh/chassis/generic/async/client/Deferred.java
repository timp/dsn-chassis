/**
 * 
 */
package org.cggh.chassis.generic.async.client;

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
	
	protected int fired = INITIAL;
	protected Object[] results = new Object[2];
	protected boolean silentlyCancelled = false;
	protected int paused = 0;
	protected Queue<Pair> chain = new LinkedList<Pair>();
	protected Function canceller = null;
	protected boolean chained = false;
	protected boolean finalized = false;
	protected Function finalizer;
	
	protected Log log = LogFactory.getLog(this.getClass());

	
	
	
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
	
	
	
	
	protected void _resback(Object res) {
//		log.enter("_resback");
		
		this.fired = (res instanceof Throwable) ? ERROR : SUCCESS;
//		log.debug("set fired to: "+this.fired);
		
		this.results[this.fired] = res;
		if (this.paused == 0) {
//			log.debug("firing callback chain");
			this._fire();
		}
		
//		log.leave();
	}

	

	
	protected void _check() {
		if (this.fired != INITIAL) {
			if (!this.silentlyCancelled) {
				throw new AlreadyCalledError(this);
			}
			this.silentlyCancelled = false;
			return;
		}
	}



	
	public void callback(T res) {
//		log.enter("callback");
//		log.debug("result: "+res);
		
		this._check();
		
		if (res instanceof Deferred) {
			throw new Error("Deferred instances can only be chained if they are the result of a callback");
		}
		
		this._resback(res);
		
//		log.leave();
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
		
		this.addCallback(callback);

		// add errback after callback so any errors thrown by adapter function
		// will get passed on to adapted deferred
		this.addErrback(errback); 
		
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
	protected void _fire() {
//		log.enter("_fire");

		Queue<Pair> chain = this.chain;
		int fired = this.fired;
//		log.debug("fired: "+fired);
		
		Object res = this.results[fired];
		Function cb = null;
		final Deferred self = this;
		Deferred defres = null;

//		log.debug("iterate through queue");
		for (int i=0; chain.size() > 0 && this.paused == 0; i++) {
			
//			log.debug("iteration: "+i);
//			log.debug("result: "+res+"; fired: "+fired);

//			log.debug("pick pair of head of queue");
			Pair pair = chain.poll();
			
			Function f = (fired == SUCCESS)? pair.getCallback() : pair.getErrback();

			if (f == null) {
//				log.debug("function to apply is null, continuing");
				continue;
			}
			
            try {
            	
//            	log.debug("apply function");
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
//            		log.debug("handle a deferred result");
    				defres = (Deferred) res;
            		cb = new Function() {
            			public Object apply(Object in) {
//            				log.enter("anonymous chain Function :: apply");
//            				log.debug("decrement paused");
                			self.paused--;
//                			log.debug("chain result");
                			self._resback(in);
//                			log.leave();
                			return null;
            			}
            		};
            		this.paused++;
    			}

            } catch (Throwable err) {
            	// TODO Review working
            	log.warn("caught throwable from callback function: "+err.getLocalizedMessage(), err);
            	fired = ERROR;
            	res = err;
            }	
            
		}
		
//		log.debug("left iteration loop");
		
        this.fired = fired;
        this.results[fired] = res;
        
        if (this.chain.size() == 0 && this.paused == 0 && this.finalizer != null) {
//        	log.debug("finalizing");
        	this.finalized = true;
        	this.finalizer.apply(res);
        }
        
        if (cb != null && this.paused == 1) {
        	// this is for "tail recursion" in case the dependent deferred
        	// is already fired
//        	log.debug("add callback to deferred result to chain together");
        	defres.addBoth(cb);
        	defres.chained = true;
        }
        
//        log.debug("leaving; fired: "+fired+"; paused: "+paused);
//        log.leave();
	}

	protected class Pair {
		
		protected Function callback;
		protected Function errback;

		protected Pair(Function callback, Function errback) {
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
