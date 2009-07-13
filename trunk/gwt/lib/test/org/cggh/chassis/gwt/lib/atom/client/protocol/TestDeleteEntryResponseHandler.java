/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;


import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

import static org.easymock.classextension.EasyMock.*;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestDeleteEntryResponseHandler {

	/**
	 * TODO document me
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * TODO document me
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	
	@Test
	public void onError() {
		
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
		
		// mock callback 
		DeleteEntryCallback callback = createMock(DeleteEntryCallback.class);
		// expectations
		callback.onError(request, exception); // expect call simply passed through
		replay(callback);
		
		// create testee
		DeleteEntryResponseHandler testee = new DeleteEntryResponseHandler(callback);
		
		// call method under test
		testee.onError(request, exception);
		
		// verify all
		verify(request); verify(exception); verify(callback);

	}
	
	
	
	@Test
	public void onResponseReceived_204() throws AtomFormatException {
	
		// mock request
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		// mock response
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(204);
		replay(response);
		
		// mock callback
		DeleteEntryCallback callback = createMock(DeleteEntryCallback.class);
		// expectations
		callback.onSuccess(request, response);
		replay(callback);
		
		// create testee
		DeleteEntryResponseHandler testee = new DeleteEntryResponseHandler(callback);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all
		verify(request); verify(response); verify(callback);
	}
	
	
		
	@Test
	public void onResponseReceived_404() {
		
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

		// mock callback
		DeleteEntryCallback callback = createMock(DeleteEntryCallback.class);
		// expectations
		callback.onFailure(request, response);
		replay(callback);

		// create testee
		DeleteEntryResponseHandler testee = new DeleteEntryResponseHandler(callback);

		// call method under test
		testee.onResponseReceived(request, response);

		// verify all
		verify(request); verify(response); verify(callback);
	}


}
