package org.cggh.chassis.spike.atomserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
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

	
	
	
	private void doPutFeedToCreateAtomCollection(String collectionUri) {

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

		String responseLocation = method.getResponseHeader("Location").getValue();
		assertNotNull(responseLocation);
		assertEquals(collectionUri, responseLocation);
		
		// expect the Content-Type header starts with the Atom media type
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));

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
		
		doPutFeedToCreateAtomCollection(collectionUri);

	}
	
	
	
	/**
	 * Test creation of a new atom collection by a PUT request with an atom feed
	 * document as the request body, where the atom collection is nested within
	 * another collection, e.g. "/foo/bar". 
	 */
	public void testPutFeedToCreateNestedAtomCollection() {
		
		// we want to test that we can create a new collection by PUT of atom 
		// feed doc however, we may run this test several times without cleaning
		// the database so we have to generate a random collection URI
		
		String collectionUri = SERVER_URI + Double.toString(Math.random()) + "/" + Double.toString(Math.random());

		doPutFeedToCreateAtomCollection(collectionUri);

	}
	
	
	
	
	/**
	 * Test the use of a PUT request to update the feed metadata for an already
	 * existing atom collection.
	 */
	public void testPutFeedToCreateAndUpdateAtomCollection() {
		
		// we need to create an atom collection first, to use as the target
		// for an attempt to update the feed metadata
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateAtomCollection(collectionUri);

		// now try to update feed metadata via a PUT request
		
		// setup a new PUT request
		
		PutMethod method = new PutMethod(collectionUri);

		// create the request entity
		
		RequestEntity entity = null;

		// the request body - an atom feed document
		String feedDoc = "<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection - Updated</atom:title></atom:feed>";

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
		
		// expect the status code is 200 OK - we just did an update, no creation
		
		assertEquals(200, result);

		// expect no Location header 

		Header responseLocationHeader = method.getResponseHeader("Location");
		assertNull(responseLocationHeader);
		
		// expect the Content-Type header starts with the Atom media type
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));

	}
	
	
	
	
	/**
	 * The eXist atom servlet allows the use of a POST request with a feed 
	 * document as the request entity, and responds with a 204 No Content if
	 * the request was successful. We think it is more appropriate to use a PUT
	 * request with a feed document to create a new atom collection, with a 201
	 * Created response indicating the result. However we will implement this 
	 * operation as eXist does for compatibility with clients using the eXist 
	 * atom servlet. Supporting this operation at all, and if so, the appropriate
	 * response code, may be reviewed in the near future.
	 */
	public void testPostFeedToCreateCollection() {
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());

		// setup a new POST request
		
		PostMethod method = new PostMethod(collectionUri);

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
		
		// expect the status code is 204 No Content
		
		assertEquals(204, result);

		// expect the Location header is set with an absolute URI

		String responseLocation = method.getResponseHeader("Location").getValue();
		assertNotNull(responseLocation);
		assertEquals(collectionUri, responseLocation);
		
		// expect no Content-Type header 
		
		Header contentTypeHeader = method.getResponseHeader("Content-Type");
		assertNull(contentTypeHeader);

	}
	
	
	
	
	/**
	 * Test the standard atom protocol operation to create a new member of a
	 * collection via a POST request with an atom entry document as the request
	 * entity.
	 */
	public void testPostEntryToCreateMember() {
		
		fail("TODO");

	}
}
