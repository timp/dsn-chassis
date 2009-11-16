/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import junit.framework.TestCase;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class TestGetEntryCallback extends TestCase {

	
	
	class TestFunction<I,O> implements Function<I,O> {
		int called = 0;
		public O apply(I in) {
			called++;
			return null;
		}
	}
	
	
	
	/**
	 * Test method for {@link legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)}.
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
		HttpDeferred deferred = createMock(HttpDeferred.class);
		// expectations
		deferred.errback(exception);
		replay(deferred);
		
		// create object under test
		GetEntryCallback testee = new GetEntryCallback(factory, deferred);
		
		// call method under test
		testee.onError(request, exception);
		
		// verify all mocks
		verify(request); verify(exception); verify(factory); verify(deferred);

	}

	
	
	/**
	 * Test method for {@link legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnResponseReceived_200() {

		// test constants
		String entryDocXML = "doesn't matter, we don't test parsing here";

		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		// mock response
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(200);
		expect(response.getStatusText()).andReturn("OK"); 
		expect(response.getHeadersAsString()).andReturn("Content-Type: application/atom+xml"); 
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(entryDocXML);
		replay(response);
		
		// mock atom entry
		AtomEntry entry = createMock(AtomEntry.class);
		// expectations
		// no calls
		replay(entry);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		expect(factory.createEntry(entryDocXML)).andReturn(entry);
		replay(factory);
		
		// mock deferred
		HttpDeferred deferred = createMock(HttpDeferred.class);
		// expectations
		deferred.addRequest(request);
		deferred.addResponse(response);
		deferred.callback(entry);
		replay(deferred);
		
		// create object under test
		GetEntryCallback testee = new GetEntryCallback(factory, deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all mocks
		verify(request); verify(response); verify(entry); verify(factory); verify(deferred);
		
	}

	
	
	/**
	 * Test method for {@link legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnResponseReceived_200_AtomFormatException() {

		// test constants
		String entryDocXML = "doesn't matter, we don't test parsing here";

		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		// mock response
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(200);
		expect(response.getStatusText()).andReturn("OK"); 
		expect(response.getHeadersAsString()).andReturn("Content-Type: application/atom+xml"); 
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(entryDocXML);
		replay(response);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		AtomFormatException exception = new AtomFormatException("test exception");
		// expectations
		expect(factory.createEntry(entryDocXML)).andThrow(exception);
		replay(factory);
		
		// mock deferred
		HttpDeferred deferred = createMock(HttpDeferred.class);
		// expectations
		deferred.addRequest(request);
		deferred.addResponse(response);
		deferred.errback(exception);
		replay(deferred);
		
		// create object under test
		GetEntryCallback testee = new GetEntryCallback(factory, deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all mocks
		verify(request); verify(response); verify(factory); verify(deferred);
		
	}

	
	
	/**
	 * Test method for {@link legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GetEntryCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)}.
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
		expect(response.getStatusText()).andReturn("Not Found"); 
		expect(response.getHeadersAsString()).andReturn("foo: bar"); 
		expect(response.getHeader("Content-Type")).andReturn("text/plain"); 
		expect(response.getText()).andReturn("resource was not found"); 
		replay(response);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		replay(factory);
		
		// deferred
		HttpDeferred deferred = new HttpDeferred();
		
		// test callback
		TestFunction callback = new TestFunction();

		// test errback
		TestFunction errback = new TestFunction();
		
		deferred.addCallbacks(callback, errback);
		
		// create object under test
		GetEntryCallback testee = new GetEntryCallback(factory, deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);

		// check errback was called
		assertEquals(0, callback.called);
		assertEquals(1, errback.called);
		
		// verify all mocks
		verify(request); verify(response); verify(factory);
		
	}

	
	

}
