/**
 * 
 */
package org.cggh.chassis.generic.async.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.log.client.SystemOutLog;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



/**
 * @author aliman
 *
 */
public class TestDeferred {
	
	
	
	static {
		// need to change because default is gwt log, which won't output in plain junit tests
		LogFactory.create = SystemOutLog.create;
	}
	
	
	public TestDeferred() {}
	
	private abstract class HelperFunction<I,O> implements Function<I,O> {
		protected int called = 0;
	}

	private Log log = LogFactory.getLog(this.getClass());
	
	@Before
	public void setUp() {
	}

	@Test
	public void testCallback() {
		log.enter("testCallback");
		
		Deferred<String> deferred = new Deferred<String>();
		
		// test variables
		final String testString = "hello world!";

		// test callback function
		HelperFunction<String,String> callback = new HelperFunction<String,String>() {
			public String apply(String s) {
				called++;
				assertEquals(testString, s);
				return null;
			}
		};
		
		// check initial state
		assertEquals(Deferred.INITIAL, deferred.getStatus());
		assertEquals(0, callback.called);

		// add callback function
		deferred.addCallback(callback);
		
		// callback deferred
		deferred.callback(testString);
		
		// test final state
		assertEquals(Deferred.SUCCESS, deferred.getStatus());
		assertEquals(1, callback.called);
		
		log.leave();
	}
	
	@Test
	public void testAddCallbackTestFunctionAfterCallback() {
		log.enter("testAddCallbackFunctionAfterCallback");
		
		Deferred<String> deferred = new Deferred<String>();
		
		// test variables
		final String testString = "hello world!";

		// test callback function
		HelperFunction<String,String> callback = new HelperFunction<String,String>() {
			public String apply(String s) {
				called++;
				assertEquals(testString, s);
				return null;
			}
		};
		
		// check initial state
		assertEquals(Deferred.INITIAL, deferred.getStatus());
		assertEquals(0, callback.called);

		// callback deferred first
		deferred.callback(testString);

		// test intermediate state
		assertEquals(Deferred.SUCCESS, deferred.getStatus());
		assertEquals(0, callback.called);
		
		// add callback function
		deferred.addCallback(callback);

		// test final state
		assertEquals(Deferred.SUCCESS, deferred.getStatus());
		assertEquals(1, callback.called);
		
		log.leave();
	}
	
	@Test
	public void testErrback() {
		log.enter("testErrback");
		
		Deferred<String> deferred = new Deferred<String>();
		
		// test variables
		final Throwable testException = new Exception("test exception");

		// test errback function
		HelperFunction<Throwable,Throwable> errback = new HelperFunction<Throwable,Throwable>() {
			public Throwable apply(Throwable t) {
				called++;
				assertEquals(testException, t);
				return t;
			}
		};
		
		// check initial state
		assertEquals(Deferred.INITIAL, deferred.getStatus());
		assertEquals(0, errback.called);

		// add errback function
		deferred.addErrback(errback);
		
		// errback deferred
		deferred.errback(testException);
		
		// test final state
		assertEquals(1, errback.called);
		assertEquals(Deferred.ERROR, deferred.getStatus());

		log.leave();
	}
	
	@Test
	public void testAddErrbackFunctionAfterErrback() {
		log.enter("testAddErrbackFunctionAfterErrback");
		
		Deferred<String> deferred = new Deferred<String>();
		
		// test variables
		final Throwable testException = new Exception("test exception");

		// test errback function
		HelperFunction<Throwable,Throwable> errback = new HelperFunction<Throwable,Throwable>() {
			public Throwable apply(Throwable t) {
				called++;
				assertEquals(testException, t);
				return t;
			}
		};
		
		// check initial state
		assertEquals(Deferred.INITIAL, deferred.getStatus());
		assertEquals(0, errback.called);

		// errback deferred first
		deferred.errback(testException);
		
		// test intermediate state
		assertEquals(Deferred.ERROR, deferred.getStatus());
		assertEquals(0, errback.called);
		
		// add errback function
		deferred.addErrback(errback);
		
		// test final state
		assertEquals(1, errback.called);
		assertEquals(Deferred.ERROR, deferred.getStatus());

		log.leave();
	}
	
	@Test
	public void testThreeCallbacks() {
		log.enter("testTwoCallbacks");
		
		// deferred under test
		Deferred<String> deferred = new Deferred<String>();
		
		// test variables
		final String s = "21";
		final Integer i = 21;
		final Integer j = 42;
		
		// first callback function
		HelperFunction<String,Integer> first = new HelperFunction<String,Integer>() {
			public Integer apply(String in) {
				called++;
				assertEquals(s, in);
				return Integer.parseInt(in);
			}
		};
		
		// second callback function
		HelperFunction<Integer,Integer> second = new HelperFunction<Integer,Integer>() {
			public Integer apply(Integer in) {
				called++;
				assertEquals(i, in);
				return in * 2;
			}
		};

		// check initial state
		assertEquals(Deferred.INITIAL, deferred.getStatus());
		assertEquals(0, first.called);
		assertEquals(0, second.called);
		
		// add callback functions
		deferred.addCallback(first);
		deferred.addCallback(second);
		
		// callback deferred
		deferred.callback(s);
		
		// check new state
		assertEquals(Deferred.SUCCESS, deferred.getStatus());
		assertEquals(1, first.called);
		assertEquals(1, second.called);
		
		// try another callback
		HelperFunction<Integer,Boolean> third = new HelperFunction<Integer,Boolean>() {
			public Boolean apply(Integer in) {
				called++;
				assertEquals(j, in);
				return true;
			}
		};
		
		// check state prior to adding
		assertEquals(0, third.called);
		
		// add third callback
		deferred.addCallback(third);
		
		// check new state
		assertEquals(Deferred.SUCCESS, deferred.getStatus());
		assertEquals(1, first.called);
		assertEquals(1, second.called);
		assertEquals(1, third.called);
		
		log.leave();
	}
	
	@Test
	public void testCallbackThrows() {
		log.enter("testCallbackThrows");
		
		// deferred under test
		Deferred<String> deferred = new Deferred<String>();
		
		// test variables
		final String foo = "foo";
		final String bar = "bar";
		
		// callback function
		HelperFunction<String,String> callback = new HelperFunction<String,String>() {
			public String apply(String in) {
				called++;
				assertEquals(foo, in);
				throw new Error("bar");
			}
		};
		
		// errback
		HelperFunction<Throwable,Throwable> errback = new HelperFunction<Throwable,Throwable>() {
			public Throwable apply(Throwable in) {
				called++;
				assertEquals(bar, in.getLocalizedMessage());
				return in;
			}
		};
		
		// check initial state
		assertEquals(Deferred.INITIAL, deferred.getStatus());
		assertEquals(0, callback.called);
		assertEquals(0, errback.called);
		
		// add callback
		deferred.addCallback(callback);
		
		// add errback
		deferred.addErrback(errback);
		
		// callback deferred
		deferred.callback(foo);
		
		// check new state
		assertEquals(Deferred.ERROR, deferred.getStatus());
		assertEquals(1, callback.called);
		assertEquals(1, errback.called);
		
		log.leave();
	}

	@Test
	public void testCallbackReturnsThrowable() {
		log.enter("testCallbackReturnsThrowable");
		
		// deferred under test
		Deferred<String> deferred = new Deferred<String>();
		
		// test variables
		final String foo = "foo";
		final String bar = "bar";
		
		// callback function
		HelperFunction<String,Object> callback = new HelperFunction<String,Object>() {
			public Object apply(String in) {
				called++;
				assertEquals(foo, in);
				return new Error("bar");
			}
		};
		
		// errback
		HelperFunction<Throwable,Throwable> errback = new HelperFunction<Throwable,Throwable>() {
			public Throwable apply(Throwable in) {
				called++;
				assertEquals(bar, in.getLocalizedMessage());
				return in;
			}
		};
		
		// check initial state
		assertEquals(Deferred.INITIAL, deferred.getStatus());
		assertEquals(0, callback.called);
		assertEquals(0, errback.called);
		
		// add callback
		deferred.addCallback(callback);
		
		// add errback
		deferred.addErrback(errback);
		
		// callback deferred
		deferred.callback(foo);
		
		// check new state
		assertEquals(Deferred.ERROR, deferred.getStatus());
		assertEquals(1, callback.called);
		assertEquals(1, errback.called);
		
		log.leave();
	}
	
	@Test
	public void testCallbackReturnsDeferred() {
		log.enter("testCallbackReturnsDeferred");

		log.debug("create deferred under test");
		Deferred<String> def1 = new Deferred<String>();
		final Deferred<Integer> def2 = new Deferred<Integer>();

		log.debug("create test variables");
		final String s = "42";

		log.debug("create callback functions");
		
		HelperFunction<String,Deferred<Integer>> first = new HelperFunction<String,Deferred<Integer>>() {
			public Deferred<Integer> apply(String in) {
				called++;
				assertEquals(s, in);
				return def2;
			}
		};
		
		HelperFunction<Integer,Boolean> second = new HelperFunction<Integer,Boolean>() {
			public Boolean apply(Integer in) {
				called++;
				assertEquals(new Integer(42), in);
				return true;
			}
		};

		log.debug("check initial state");
		assertEquals(Deferred.INITIAL, def1.getStatus());
		assertEquals(Deferred.INITIAL, def2.getStatus());
		assertEquals(0, def1.getPaused());
		assertEquals(0, def2.getPaused());
		assertEquals(0, first.called);
		assertEquals(0, second.called);
		
		log.debug("add callback functions to def1");
		def1.addCallback(first);
		def1.addCallback(second);
		
		log.debug("callback def1");
		def1.callback(s);
		
		log.debug("check state");
		assertEquals(Deferred.SUCCESS, def1.getStatus());
		assertEquals(Deferred.INITIAL, def2.getStatus()); // has not been called yet
		assertEquals(1, def1.getPaused()); // def1 should be paused, awaiting callback on def2
		assertEquals(0, def2.getPaused());
		assertEquals(1, first.called); // should have been called
		assertEquals(0, second.called); // should not be called until callback on def2
		
		log.debug("callback def2");
		def2.callback(42);

		log.debug("check state");
		assertEquals(Deferred.SUCCESS, def1.getStatus());
		assertEquals(Deferred.SUCCESS, def2.getStatus());
		assertEquals(0, def1.getPaused()); // def1 should no longer be paused
		assertEquals(0, def2.getPaused());
		assertEquals(1, first.called); // should have been called
		assertEquals(1, second.called); // should now have been called

		log.leave();
	}

	
	
	/**
	 * Test that an exception thrown from an adapter function is passed on
	 * to an adapted deferred.
	 */
	@Test
	public void testAdapt_errBack() {
		log.enter("testAdapt_errBack");
		
		Deferred<String> deferred = new Deferred<String>();
		
		final RuntimeException dummy = new RuntimeException("dummy exception");
		
		Function<String,Integer> adapter = new Function<String,Integer>() {

			public Integer apply(String in) {
				throw dummy;
			}
			
		};
		
		Deferred<Integer> adapted = deferred.adapt(adapter);

		deferred.callback("42"); // doesn't matter what string we use here
		
		assertEquals(Deferred.ERROR, deferred.getStatus()); // original deferred should be in error
		assertSame(dummy, deferred.getErrorResult());
		
		assertEquals(Deferred.ERROR, adapted.getStatus()); // adapted deferred should also be in error
		assertSame(dummy, adapted.getErrorResult());
		
		log.leave();
	}
	
}
