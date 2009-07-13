/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;


import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetEntryCallback;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetEntryResponseHandler;
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
public class TestGetEntryResponseHandler {

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
		
		// mock atom factory 
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		// (no calls)
		replay(factory);
		
		// mock callback 
		GetEntryCallback callback = createMock(GetEntryCallback.class);
		// expectations
		callback.onError(request, exception); // expect call simply passed through
		replay(callback);
		
		// create testee
		GetEntryResponseHandler testee = new GetEntryResponseHandler(callback, factory);
		
		// call method under test
		testee.onError(request, exception);
		
		// verify all
		verify(request); verify(exception); verify(factory); verify(callback);

	}
	
	
	
	@Test
	public void onResponseReceived_200() throws AtomFormatException {
	
		// test constants
		String entryDocXML = "doesn't matter";

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
		expect(response.getText()).andReturn(entryDocXML);
		replay(response);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		expect(factory.createEntry(entryDocXML)).andReturn(null);
		replay(factory);
		
		// mock callback
		GetEntryCallback callback = createMock(GetEntryCallback.class);
		// expectations
		callback.onSuccess(request, response, null);
		replay(callback);
		
		// create testee
		GetEntryResponseHandler testee = new GetEntryResponseHandler(callback, factory);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all
		verify(request); verify(response); verify(factory); verify(callback);
	}
	
	
	
	@Test
	public void onResponseReceived_200_formatException() throws AtomFormatException {

		// test constants
		String entryDocXML = "doesn't matter";

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
		expect(response.getText()).andReturn(entryDocXML);
		replay(response);
		
		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		AtomFormatException exception = new AtomFormatException("document is not well-formed");
		// expectations
		expect(factory.createEntry(entryDocXML)).andThrow(exception);
		replay(factory);
		
		// mock callback
		GetEntryCallback callback = createMock(GetEntryCallback.class);
		// expectations
		callback.onError(request, response, exception);
		replay(callback);
		
		// create testee
		GetEntryResponseHandler testee = new GetEntryResponseHandler(callback, factory);
		
		// call method under test
		testee.onResponseReceived(request, response);
		
		// verify all
		verify(request); verify(response); verify(factory); verify(callback);
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

		// mock atom factory
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		// (no calls)
		replay(factory);
		
		// mock callback
		GetEntryCallback callback = createMock(GetEntryCallback.class);
		// expectations
		callback.onFailure(request, response);
		replay(callback);

		// create testee
		GetEntryResponseHandler testee = new GetEntryResponseHandler(callback, factory);

		// call method under test
		testee.onResponseReceived(request, response);
	}


}
