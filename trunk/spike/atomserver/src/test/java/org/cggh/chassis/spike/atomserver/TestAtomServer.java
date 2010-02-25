package org.cggh.chassis.spike.atomserver;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import junit.framework.TestCase;

public class TestAtomServer extends TestCase {
	
	
	
	
	private static final String SERVER_URL = "http://localhost:8081/atomserver/atomserver/";
	
	
	
	
	public void testPutFeed_Create() {
		
		try {
			
			// we want to test that we can create a new collection by PUT of atom feed doc
			// however, we may run this test several times without cleaning the database
			// so we have to generate a random collection URI
			
			String collectionUri = SERVER_URL + Double.toString(Math.random());
			
			PutMethod method = new PutMethod(collectionUri);

			RequestEntity entity = new StringRequestEntity(
					"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>",
					"application/atom+xml;type=feed",
					"utf-8");
			
			method.setRequestEntity(entity);
			
			HttpClient client = new HttpClient();
			
			int result = client.executeMethod(method);
			assertEquals(201, result);
			
			String location = method.getResponseHeader("Location").getValue();
			assertNotNull(location);
			assertEquals(collectionUri, location);

		}
		catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
		
	}
	
	
	
	
	public void testPutNestedFeed_Create() {
		
		try {
			
			// we want to test that we can create a new collection by PUT of atom feed doc
			// where the collection is nested in another collection
			
			String collectionUri = SERVER_URL + Double.toString(Math.random()) + "/" + Double.toString(Math.random());
			
			PutMethod method = new PutMethod(collectionUri);

			RequestEntity entity = new StringRequestEntity(
					"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>",
					"application/atom+xml;type=feed",
					"utf-8");
			
			method.setRequestEntity(entity);
			
			HttpClient client = new HttpClient();
			
			int result = client.executeMethod(method);
			assertEquals(201, result);
			
			String location = method.getResponseHeader("Location").getValue();
			assertNotNull(location);
			assertEquals(collectionUri, location);

		}
		catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
		
	}
	
	
	
	
	public void testPutFeed_CreateAndUpdate() {
		
		try {
			
			// we want to test that we can create a new collection by PUT of atom feed doc
			// however, we may run this test several times without cleaning the database
			// so we have to generate a random collection URI
			
			String collectionUri = SERVER_URL + Double.toString(Math.random());
			
			PutMethod method = new PutMethod(collectionUri);

			RequestEntity entity = new StringRequestEntity(
					"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>",
					"application/atom+xml;type=feed",
					"utf-8");
			
			method.setRequestEntity(entity);
			
			HttpClient client = new HttpClient();
			
			int result = client.executeMethod(method);
			assertEquals(201, result);
			
			Header location = method.getResponseHeader("Location");
			assertNotNull(location);
			assertEquals(collectionUri, location.getValue());

			// now try to update
			
			method = new PutMethod(collectionUri);

			entity = new StringRequestEntity(
					"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection - Updated</atom:title></atom:feed>",
					"application/atom+xml;type=feed",
					"utf-8");
			
			method.setRequestEntity(entity);
			
			result = client.executeMethod(method);
			assertEquals(200, result);
			
			location = method.getResponseHeader("Location");
			assertNull(location);

		}
		catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
		
	}
	
	
	
	


}
