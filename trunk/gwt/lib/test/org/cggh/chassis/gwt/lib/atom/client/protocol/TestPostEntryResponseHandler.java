/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;


import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
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
public class TestPostEntryResponseHandler {

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
		
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);

		Throwable exception = createMock(Throwable.class);
		// expectations
		// (no calls)
		replay(exception);
		
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		// (no calls)
		replay(factory);

		PostEntryCallback callback = createMock(PostEntryCallback.class);
		// expectations
		callback.onError(request, exception); // i.e., call is simply passed through
		replay(callback);
		
		PostEntryResponseHandler testee = new PostEntryResponseHandler(callback, factory);
		testee.onError(request, exception);
		
		verify(request);
		verify(exception);
		verify(factory);
		verify(callback);
		
	}

	
	
	@Test
	public void onResponseReceived_201() throws AtomFormatException {
		
		// test constants
		String entryDocXML = "doesn't matter, we don't test parsing here";
		
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);
		
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(201);
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(entryDocXML);
		replay(response);
		
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		expect(factory.createEntry(entryDocXML)).andReturn(null);
		replay(factory);
		
		PostEntryCallback callback = createMock(PostEntryCallback.class);
		// expectations
		callback.onSuccess(request, response, null);
		replay(callback);
		
		PostEntryResponseHandler testee = new PostEntryResponseHandler(callback, factory);
		testee.onResponseReceived(request, response);
		
		verify(request);
		verify(response);
		verify(factory);
		
	}
	
	
	
	@Test
	public void onResponseReceived_404() {

		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);
		
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(404);
		replay(response);

		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		// (no calls)
		replay(factory);

		PostEntryCallback callback = createMock(PostEntryCallback.class);
		// expectations
		callback.onFailure(request, response); 
		replay(callback);

		PostEntryResponseHandler testee = new PostEntryResponseHandler(callback, factory);
		testee.onResponseReceived(request, response);
		
		verify(request);
		verify(response);
		verify(factory);

	}

	@Test
	public void onResponseReceived_201_formatException() throws AtomFormatException {

		String entryDocXML = "doesn't matter, we don't test parsing here";

		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);
		
		Response response = createMock(Response.class);
		expect(response.getStatusCode()).andReturn(201);
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(entryDocXML);
		replay(response);
		
		AtomFactory factory = createMock(AtomFactory.class);
		AtomFormatException exception = new AtomFormatException("document is not well-formed");
		// expectations
		expect(factory.createEntry(entryDocXML)).andThrow(exception);
		replay(factory);

		PostEntryCallback callback = createMock(PostEntryCallback.class);
		// expectations
		callback.onError(request, response, exception); 
		replay(callback);

		PostEntryResponseHandler testee = new PostEntryResponseHandler(callback, factory);
		testee.onResponseReceived(request, response);
		
		verify(request);
		verify(response);
		verify(factory);

	}

	
}
