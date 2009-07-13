/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.protocol.GetEntryRequestBuilder;

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
public class TestGetEntryRequestBuilder extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.atom.Atom";
	}
	
	public void testBuildHTTPRequest() {
		
		String entryURL = "/atom/edit/mycollection?id=foo";
		
		GetEntryRequestBuilder builder = new GetEntryRequestBuilder(URL.encode(entryURL));
		
		RequestBuilder http = builder.buildHTTPRequest();
		
		String expectedMethod = "GET";
		assertEquals(expectedMethod, http.getHTTPMethod());
		
		String expectedAcceptHeader = "application/atom+xml,application/xml";
		assertEquals(expectedAcceptHeader, http.getHeader("Accept"));
		
		String expectedUrl = URL.encode(entryURL);
		assertEquals(expectedUrl, http.getUrl());
		
	}
	
	public void testSendWithoutCallback() {

		String entryURL = "/atom/edit/mycollection?id=foo";
		
		GetEntryRequestBuilder builder = new GetEntryRequestBuilder(URL.encode(entryURL));

		try {
			builder.send();
			fail("expected exception as no callback set");
		} catch (RequestException ex) {
			System.out.println("expected exception caught: "+ex.getLocalizedMessage());
		}
		
	}


}
