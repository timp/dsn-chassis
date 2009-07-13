/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestPostEntryRequestBuilder extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.atom.Atom";
	}
	
	public void testBuildHTTPRequest() throws AtomFormatException {
		
		// test constants
		String collectionURL = "/atom/edit/mycollection";

		AtomEntry entry = new AtomEntry();
		entry.setTitle("test entry");
		entry.setSummary("test summary foo bar");
		
		PostEntryRequestBuilder builder = new PostEntryRequestBuilder(URL.encode(collectionURL));
		builder.setEntry(entry);
		
		RequestBuilder http = builder.buildHTTPRequest();
		
		String expectedMethod = "POST";
		assertEquals(expectedMethod, http.getHTTPMethod());
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, http.getHeader("Accept"));
		
		String expectedContentTypeHeader = "application/atom+xml;type=entry;charset=\"utf-8\"";
		assertEquals(expectedContentTypeHeader, http.getHeader("Content-Type"));
		
		String expectedContentLengthHeader = Integer.toString(entry.toString().length());
		assertEquals(expectedContentLengthHeader, http.getHeader("Content-Length"));
			
		String expectedUrl = URL.encode(collectionURL);
		assertEquals(expectedUrl, http.getUrl());
		
		String expectedData = entry.toString();
		assertEquals(expectedData, http.getRequestData());
		
	}
	
	public void testSendWithoutCallback() throws AtomFormatException {

		// test constants
		String collectionURL = "/atom/edit/mycollection";

		AtomEntry entry = new AtomEntry();
		entry.setTitle("test entry");
		entry.setSummary("test summary foo bar");
		
		PostEntryRequestBuilder builder = new PostEntryRequestBuilder(URL.encode(collectionURL));

		builder.setEntry(entry);
		
		try {
			builder.send();
			fail("expected exception as no callback set");
		} catch (RequestException ex) {
			System.out.println("expected exception caught: "+ex.getLocalizedMessage());
		}
		
	}

	public void testSendWithoutEntry() {

		// test constants
		String collectionURL = "/atom/edit/mycollection";

		PostEntryRequestBuilder builder = new PostEntryRequestBuilder(URL.encode(collectionURL));

		builder.setCallback(new PostEntryCallback() {

			public void onError(Request request, Throwable exception) {
				// do nothing
			}

			public void onError(Request request, Response response, Throwable exception) {
				// do nothing
			}

			public void onFailure(Request request, Response response) {
				// do nothing
			}

			public void onSuccess(Request request, Response response, AtomEntry entry) {
				// do nothing
			}});
		
		try {
			builder.send();
			fail("expected exception as no entry set");
		} catch (RequestException ex) {
			System.out.println("expected exception caught: "+ex.getLocalizedMessage());
		}
		
	}

}
