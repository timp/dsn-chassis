/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import org.cggh.chassis.generic.twisted.client.HttpException;
import org.cggh.chassis.generic.twisted.client.HttpDeferred;
import org.cggh.chassis.generic.twisted.client.Function;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

import junit.framework.TestCase;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.CallbackWithNoContent;

/**
 * @author aliman
 *
 */
public class TestDeleteEntryCallback extends TestCase {

	
	
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
		
		// mock deferred
		HttpDeferred deferred = createMock(HttpDeferred.class);
		// expectations
		deferred.errback(exception);
		replay(deferred);
		
		// create object under test
		CallbackWithNoContent testee = new CallbackWithNoContent(deferred);
		
		// call method under test
		testee.onError(request, exception);
		
		// verify all mocks
		verify(request); verify(exception); verify(deferred);

	}
	
	
	
	@SuppressWarnings("unchecked")
	public void testOnResponseReceived_204() {
		
		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		// mock response
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(204); // 204 NO CONTENT 
		replay(response);
		
		// mock deferred
		HttpDeferred deferred = createMock(HttpDeferred.class);
		// expectations
		deferred.callback(null);
		replay(deferred);
		
		// create object under test
		CallbackWithNoContent testee = new CallbackWithNoContent(deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all mocks
		verify(request); verify(response); verify(deferred);
		
	}
	

	
	
	class TestFunction<I,O> implements Function<I,O> {
		int called = 0;
		public O apply(I in) {
			called++;
			return null;
		}
	}
	
	
	
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
		
		// deferred
		HttpDeferred deferred = new HttpDeferred();
		
		// test callback
		TestFunction callback = new TestFunction();

		// test errback
		TestFunction errback = new TestFunction();
		
		deferred.addCallbacks(callback, errback);
		
		// create object under test
		CallbackWithNoContent testee = new CallbackWithNoContent(deferred);
		
		// call method under test
		testee.onResponseReceived(request, response);

		// check errback was called
		assertEquals(0, callback.called);
		assertEquals(1, errback.called);
		assertNull(deferred.getSuccessResult());
		assertTrue(deferred.getErrorResult() instanceof HttpException);
		
		// verify all mocks
		verify(request); verify(response);
		
	}

	
}
