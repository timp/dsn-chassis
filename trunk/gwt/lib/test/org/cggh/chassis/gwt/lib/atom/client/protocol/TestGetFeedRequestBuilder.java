/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedRequestBuilder;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestGetFeedRequestBuilder extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.atom.Atom";
	}
	
	public void testBuildHTTPRequest() {
		
		String collectionURL = "/atom/edit/mycollection";
		
		GetFeedRequestBuilder builder = new GetFeedRequestBuilder(URL.encode(collectionURL));
		
		RequestBuilder http = builder.buildHTTPRequest();
		
		String expectedMethod = "GET";
		assertEquals(expectedMethod, http.getHTTPMethod());
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, http.getHeader("Accept"));
		
		String expectedUrl = URL.encode(collectionURL);
		assertEquals(expectedUrl, http.getUrl());
		
	}
	
	public void testSendWithoutCallback() {

		String collectionURL = "/atom/edit/mycollection";
		
		GetFeedRequestBuilder builder = new GetFeedRequestBuilder(URL.encode(collectionURL));

		try {
			builder.send();
			fail("expected exception as no callback set");
		} catch (RequestException ex) {
			System.out.println("expected exception caught: "+ex.getLocalizedMessage());
		}
		
	}

}
