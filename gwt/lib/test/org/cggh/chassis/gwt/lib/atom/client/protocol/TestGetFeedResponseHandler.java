/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;


import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedCallback;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedResponseHandler;
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
public class TestGetFeedResponseHandler {

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

		GetFeedCallback callback = createMock(GetFeedCallback.class);
		// expectations
		callback.onError(request, exception); // i.e., call is simply passed through
		replay(callback);
		
		GetFeedResponseHandler testee = new GetFeedResponseHandler(callback, factory);
		testee.onError(request, exception);
		
		verify(request);
		verify(exception);
		verify(factory);
		verify(callback);
		
	}

	
	
	@Test
	public void onResponseReceived_200() throws AtomFormatException {
		
		// test constants
		String feedDocXML = "<feed xmlns=\"http://www.w3.org/2005/AtomNS\"><title>test feed</title></feed>";
		
		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);
		
		Response response = createMock(Response.class);
		// expectations
		expect(response.getStatusCode()).andReturn(200);
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(feedDocXML);
		replay(response);
		
		AtomFactory factory = createMock(AtomFactory.class);
		// expectations
		expect(factory.createFeed(feedDocXML)).andReturn(null);
		replay(factory);
		
		GetFeedCallback callback = createMock(GetFeedCallback.class);
		// expectations
		callback.onSuccess(request, response, null);
		replay(callback);
		
		GetFeedResponseHandler testee = new GetFeedResponseHandler(callback, factory);
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

		GetFeedCallback callback = createMock(GetFeedCallback.class);
		// expectations
		callback.onFailure(request, response); 
		replay(callback);

		GetFeedResponseHandler testee = new GetFeedResponseHandler(callback, factory);
		testee.onResponseReceived(request, response);
		
		verify(request);
		verify(response);
		verify(factory);

	}

	@Test
	public void onResponseReceived_200_formatException() throws AtomFormatException {

		String feedDocXML = "<feed xmlns=\"http://www.w3.org/2005/AtomNS\"><notwellformed></feed>";

		Request request = createMock(Request.class);
		// expectations
		// (no calls)
		replay(request);
		
		Response response = createMock(Response.class);
		expect(response.getStatusCode()).andReturn(200);
		expect(response.getHeader("Content-Type")).andReturn("application/atom+xml");
		expect(response.getText()).andReturn(feedDocXML);
		replay(response);
		
		AtomFactory factory = createMock(AtomFactory.class);
		AtomFormatException exception = new AtomFormatException("document is not well-formed");
		// expectations
		expect(factory.createFeed(feedDocXML)).andThrow(exception);
		replay(factory);

		GetFeedCallback callback = createMock(GetFeedCallback.class);
		// expectations
		callback.onError(request, response, exception); 
		replay(callback);

		GetFeedResponseHandler testee = new GetFeedResponseHandler(callback, factory);
		testee.onResponseReceived(request, response);
		
		verify(request);
		verify(response);
		verify(factory);

	}

	
}
