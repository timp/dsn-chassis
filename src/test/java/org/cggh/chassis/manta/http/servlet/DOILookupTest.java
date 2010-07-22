package org.cggh.chassis.manta.http.servlet;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
/**
 * Not so much a test as documentation.
 */
public class DOILookupTest extends TestCase {

	public static final HttpClient client = new HttpClient();

	public void testGoodGet() throws Exception {
		String doi ="10.1371/journal.pgen.1000508";
		HttpMethod get = new GetMethod("http://dx.doi.org/" + doi);
		client.executeMethod(get);
		assertEquals(200,get.getStatusCode());
		assertFalse(new String(get.getResponseBody()).contains("Not Found"));
	}

	public void testBadNamingAuthorityGet() throws Exception {
		
		HttpMethod get = new GetMethod("http://dx.doi.org/bad");
		client.executeMethod(get);
		assertEquals(200,get.getStatusCode());
		assertTrue(new String(get.getResponseBody()).contains("DOI Naming Authority"));
		assertTrue(new String(get.getResponseBody()).contains("Not Found"));
	}
	public void testGoodNamingAuthorityBadDOIGet() throws Exception {
		
		HttpMethod get = new GetMethod("http://dx.doi.org/10.1186/1471-2105-8-487bad");
		client.executeMethod(get);
		assertEquals(200,get.getStatusCode());
		assertTrue(new String(get.getResponseBody()).contains("Not Found"));
			
	}
	
	
}
