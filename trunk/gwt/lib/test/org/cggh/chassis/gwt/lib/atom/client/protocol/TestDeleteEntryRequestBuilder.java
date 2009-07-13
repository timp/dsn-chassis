/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
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
public class TestDeleteEntryRequestBuilder extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.gwt.lib.atom.Atom";
	}
	
	public void testBuildHTTPRequest() throws AtomFormatException {
		
		// test constants
		String entryURL = "/atom/edit/mycollection?id=foo";

		DeleteEntryRequestBuilder builder = new DeleteEntryRequestBuilder(URL.encode(entryURL));
		
		RequestBuilder http = builder.buildHTTPRequest();
		
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
	
	
	public void testSendWithoutCallback() throws AtomFormatException {

		// test constants
		String entryURL = "/atom/edit/mycollection?id=foo";

		DeleteEntryRequestBuilder builder = new DeleteEntryRequestBuilder(URL.encode(entryURL));

		try {
			builder.send();
			fail("expected exception as no callback set");
		} catch (RequestException ex) {
			System.out.println("expected exception caught: "+ex.getLocalizedMessage());
		}
		
	}


}
