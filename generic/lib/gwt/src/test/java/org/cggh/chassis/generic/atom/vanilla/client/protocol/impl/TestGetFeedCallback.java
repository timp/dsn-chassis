/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import junit.framework.TestCase;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class TestGetFeedCallback extends TestCase {

	
	
	class TestFunction<I,O> implements Function<I,O> {
		int called = 0;
		public O apply(I in) {
			called++;
			return null;
		}
	}
	
	
	
	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnError() {
		
		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);
		
		// mock exception 
		Throwable exception = createMock(Throwable.class);
		// expectations
		// (no calls)
		replay(exception);
		
		// mock atom factory 
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		// (no calls)
		replay(factory);
		
		// mock deferred
		Deferred deferred = createMock(Deferred.class);
		// expectations
		deferred.errback(exception);
		replay(deferred);
		
		// create object under test
		GetFeedCallback testee = new GetFeedCallback(factory, deferred);
		
		// call method under test
		testee.onError(request, exception);
		
		// verify all mocks
		verify(request); verify(exception); verify(factory); verify(deferred);

	}

	
	
	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnResponseReceived_200() {

		// test constants
		String feedDocXML = "doesn't matter, we don't test parsing here";

		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		// mock response
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(200);
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(feedDocXML);
		replay(response);
		
		// mock atom entry
		AtomFeed feed = createMock(AtomFeed.class);
		// expectations
		// no calls
		replay(feed);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		expect(factory.createFeed(feedDocXML)).andReturn(feed);
		replay(factory);
		
		// mock deferred
		Deferred deferred = createMock(Deferred.class);
		// expectations
		deferred.callback(feed);
		replay(deferred);
		
		// create object under test
		GetFeedCallback testee = new GetFeedCallback(factory, deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all mocks
		verify(request); verify(response); verify(feed); verify(factory); verify(deferred);
		
	}

	
	
	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnResponseReceived_200_AtomFormatException() {

		// test constants
		String feedDocXML = "doesn't matter, we don't test parsing here";

		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		// mock response
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(200);
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(feedDocXML);
		replay(response);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		AtomFormatException exception = new AtomFormatException("test exception");
		// expectations
		expect(factory.createFeed(feedDocXML)).andThrow(exception);
		replay(factory);
		
		// mock deferred
		Deferred deferred = createMock(Deferred.class);
		// expectations
		deferred.errback(exception);
		replay(deferred);
		
		// create object under test
		GetFeedCallback testee = new GetFeedCallback(factory, deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all mocks
		verify(request); verify(response); verify(factory); verify(deferred);
		
	}

	
	
	/**
	 * Test method for {@link org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnResponseReceived_404() {

		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		// mock response
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(404);
		replay(response);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		replay(factory);
		
		// deferred
		Deferred deferred = new Deferred();
		
		// test callback
		TestFunction callback = new TestFunction();

		// test errback
		TestFunction errback = new TestFunction();
		
		deferred.addCallbacks(callback, errback);
		
		// create object under test
		GetFeedCallback testee = new GetFeedCallback(factory, deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);

		// check errback was called
		assertEquals(0, callback.called);
		assertEquals(1, errback.called);
		
		// verify all mocks
		verify(request); verify(response); verify(factory);
		
	}

	
	

}
