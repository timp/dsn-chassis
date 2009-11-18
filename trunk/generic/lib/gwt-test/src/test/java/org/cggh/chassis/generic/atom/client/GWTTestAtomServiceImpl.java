/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;
import com.google.gwt.junit.client.GWTTestCase;


/**
 * @author aliman
 *
 */
public class GWTTestAtomServiceImpl extends GWTTestCase {

	
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.atom.Atom";
	}
	
	
	
	
	public void testBuildGetEntryRequest() {
		
		// setup
		
		String entryURL = "/atom/edit/mycollection?id=foo";

		// call method under test
		
		RequestBuilder request = AtomServiceImpl.buildGetEntryRequest(entryURL);
		
		// test outcome
		
		String expectedMethod = "GET";
		assertEquals(expectedMethod, request.getHTTPMethod());
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, request.getHeader("Accept"));
		
		String expectedUrl = URL.encode(entryURL);
		assertEquals(expectedUrl, request.getUrl());
		
	}
	
	
	
	public void testBuildGetFeedRequest() {
		
		String feedURL = "/atom/edit/myfeed";
		
		RequestBuilder http = AtomServiceImpl.buildGetFeedRequest(feedURL);
		
		String expectedMethod = "GET";
		assertEquals(expectedMethod, http.getHTTPMethod());
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, http.getHeader("Accept"));
		
		String expectedUrl = URL.encode(feedURL);
		assertEquals(expectedUrl, http.getUrl());

	}
	
	
	
	public void testBuildPostEntryRequest() {
		
		// test constants
		String feedURL = "/atom/edit/myfeed";

		String content = "<entry xmlns='http://www.w3.org/2005/Atom'></entry>";
		RequestBuilder request = AtomServiceImpl.buildPostEntryRequest(feedURL, content);
		
		String expectedMethod = "POST";
		assertEquals(expectedMethod, request.getHTTPMethod());
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, request.getHeader("Accept"));
		
		String expectedContentTypeHeader = "application/atom+xml;type=entry;charset=\"utf-8\"";
		assertEquals(expectedContentTypeHeader, request.getHeader("Content-Type"));
		
		String expectedContentLengthHeader = Integer.toString(content.length());
		assertEquals(expectedContentLengthHeader, request.getHeader("Content-Length"));
			
		String expectedUrl = URL.encode(feedURL);
		assertEquals(expectedUrl, request.getUrl());
		
		String expectedData = content;
		assertEquals(expectedData, request.getRequestData());
		
	}
	
	
	
	public void testBuildPutEntryRequest() {
		
		// test constants
		String entryURL = "/atom/edit/mycollection?id=foo";

		String content = "<entry xmlns='http://www.w3.org/2005/Atom'></entry>";
		
		RequestBuilder request = AtomServiceImpl.buildPutEntryRequest(entryURL, content);
		
		String expectedMethod = "POST";
		assertEquals(expectedMethod, request.getHTTPMethod());
		
		String expectedMethodOverride = "PUT";
		assertEquals(expectedMethodOverride, request.getHeader("X-HTTP-Method-Override"));
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, request.getHeader("Accept"));
		
		String expectedContentTypeHeader = "application/atom+xml;type=entry;charset=\"utf-8\"";
		assertEquals(expectedContentTypeHeader, request.getHeader("Content-Type"));
		
		String expectedContentLengthHeader = Integer.toString(content.length());
		assertEquals(expectedContentLengthHeader, request.getHeader("Content-Length"));
			
		String expectedUrl = URL.encode(entryURL);
		assertEquals(expectedUrl, request.getUrl());
		
		String expectedData = content;
		assertEquals(expectedData, request.getRequestData());		
		
	}
	
	
	
	public void testBuildDeleteEntryRequest() {
		
		// test constants
		String entryURL = "/atom/edit/mycollection?id=foo";

		RequestBuilder http = AtomServiceImpl.buildDeleteEntryRequest(entryURL);
		
		String expectedMethod = "POST";
		assertEquals(expectedMethod, http.getHTTPMethod());
		
		String expectedMethodOverride = "DELETE";
		assertEquals(expectedMethodOverride, http.getHeader("X-HTTP-Method-Override"));
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, http.getHeader("Accept"));
		
		assertNull(http.getHeader("Content-Type"));
		assertNull(http.getHeader("Content-Length"));
			
		String expectedUrl = URL.encode(entryURL);
		assertEquals(expectedUrl, http.getUrl());
		
		assertNull(http.getRequestData());

	}
	
	
	

}
