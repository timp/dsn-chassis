package org.cggh.chassis.spike.atomserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import junit.framework.TestCase;

public class TestProtocol extends TestCase {


	
	
	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";
	private static final String TEST_COLLECTION_URI = SERVER_URI + "test";

	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	/**
	 * Test creation of a new atom collection by a PUT request with an atom feed
	 * document as the request body. N.B. this operation is not part of the
	 * standard atom protocol, but is an extension to support bootstrapping
	 * of an atom workspace, e.g., by an administrator.
	 */
	public void testPutFeedToCreateAtomCollection() {
		
		// we want to test that we can create a new collection by PUT of atom 
		// feed doc however, we may run this test several times without cleaning
		// the database so we have to generate a random collection URI
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());

		// setup a new PUT request
		
		PutMethod method = new PutMethod(collectionUri);

		// create the request entity
		
		RequestEntity entity = null;

		// the request body - an atom feed document
		String feedDoc = "<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>";

		String contentType = "application/atom+xml;type=feed";

		String charSet = "utf-8";
		
		try {

			entity = new StringRequestEntity(feedDoc, contentType, charSet);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		method.setRequestEntity(entity);
		
		HttpClient client = new HttpClient();
		
		int result = -1;
		
		try {

			// make the HTTP request now
			result = client.executeMethod(method);

		} catch (HttpException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		// expect the status code is 201 CREATED
		
		assertEquals(201, result);

		// expect the Location header is set with an absolute URI

		String location = method.getResponseHeader("Location").getValue();
		assertNotNull(location);
		assertEquals(collectionUri, location);
		
	}
	
	
	
}
