package org.cggh.chassis.spike.atomserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import junit.framework.TestCase;




public class TestAtomProtocolWithDefaultSecurity extends TestCase {


	
	
	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";

	
	
	
	private static final HttpClient client = new HttpClient();
	

	
	
	private static final BasicScheme basic = new BasicScheme();
	
	
	
	
	public TestAtomProtocolWithDefaultSecurity() {

		// need to run install script once for example setup
		
		String installUrl = "http://localhost:8081/atomserver/atomserver/admin/install-example.xql";
		
		GetMethod method = new GetMethod(installUrl);
		
		int result = executeMethod(method, "adam", "test");
		
		if (result != 200) {
			throw new RuntimeException("installation failed: "+result);
		}
	
	}
	
	
	
	
	private static void authenticate(HttpMethod method, String user, String pass) {
		try {
			String authorization = basic.authenticate(new UsernamePasswordCredentials(user, pass), method);
			method.setRequestHeader("Authorization", authorization);
		} 
		catch (Throwable t) {
			t.printStackTrace();
			fail(t.getLocalizedMessage());
		}
	}
	
	
	
	private static int executeMethod(HttpMethod method, String user, String pass) {
		
		authenticate(method, user, pass);
		
		Integer result = null;
		
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
		
		assertNotNull(result);
		
		return result;
		
	}
	
	
	
	private static void setAtomRequestEntity(EntityEnclosingMethod method, String xml) {
		
		RequestEntity entity = null;
		String contentType = "application/atom+xml";
		String charSet = "utf-8";
		
		try {

			entity = new StringRequestEntity(xml, contentType, charSet);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		assertNotNull(entity);
		
		method.setRequestEntity(entity);

	}
	
	
	
	
	private static String createRandomCollection(String user, String pass) {

		String collectionUri = SERVER_URI + Double.toString(Math.random());

		PutMethod method = new PutMethod(collectionUri);

		String content = "<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>";
		setAtomRequestEntity(method, content);
		
		int result = executeMethod(method, user, pass);
		
		if (result != 201)
			return null;
		
		return collectionUri;

	}
	
	
	
	
	private static String createTestEntry(String user, String pass, String collectionUri) {

		PostMethod method = new PostMethod(collectionUri);
		
		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry</atom:title><atom:summary>this is a test</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		int result = executeMethod(method, "arthur", "test");

		if (result != 201)
			return null;
		
		Header locationHeader = method.getResponseHeader("Location");
		
		assertNotNull(locationHeader);
		
		return locationHeader.getValue();

	}
	
	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	
	public void testUserWithAdministratorRoleCanCreateCollections() {
		
		// we expect the default global ACL to allow only users with the
		// ROLE_ADMINISTRATOR role to create collections
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());

		PutMethod method = new PutMethod(collectionUri);

		String content = "<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "adam" to be defined in the example
		// security config to be assigned the ROLE_ADMINISTRATOR role
		
		int result = executeMethod(method, "adam", "test");
		
		assertEquals(201, result);

	}
	
	
	
	
	public void testUserWithAdministratorRoleCanUpdateCollections() {
		
		// we expect the default global ACL to allow only users with the
		// ROLE_ADMINISTRATOR role to update collections
		
		String collectionUri = createRandomCollection("adam", "test");

		PutMethod method = new PutMethod(collectionUri);

		String content = "<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection - Updated</atom:title></atom:feed>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "adam" to be defined in the example
		// security config to be assigned the ROLE_ADMINISTRATOR role
		
		int result = executeMethod(method, "adam", "test");
		
		assertEquals(200, result);

	}
	
	
	
	
	public void testUserWithoutAdministratorRoleCannotCreateCollections() {
		
		// we expect the default global ACL to allow only users with the
		// ROLE_ADMINISTRATOR role to create collections
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());

		PutMethod method = new PutMethod(collectionUri);

		String content = "<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "rebecca" to be defined in the example
		// security config but NOT to be assigned the ROLE_ADMINISTRATOR role
		
		int result = executeMethod(method, "rebecca", "test");
		
		assertEquals(403, result);

	}
	
	
	
	
	public void testUserWithoutAdministratorRoleCannotUpdateCollections() {
		
		String collectionUri = createRandomCollection("adam", "test");

		// we expect the default global ACL to allow only users with the
		// ROLE_ADMINISTRATOR role to update collections
		
		PutMethod method = new PutMethod(collectionUri);

		String content = "<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection - Updated</atom:title></atom:feed>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "rebecca" to be defined in the example
		// security config but NOT to be assigned the ROLE_ADMINISTRATOR role
		
		int result = executeMethod(method, "rebecca", "test");
		
		assertEquals(403, result);

	}
	
	
	
	

	
	public void testUserWithAuthorRoleCanCreateAtomEntries() {
		
		String collectionUri = createRandomCollection("adam", "test");

		// we expect the default collection ACL to allow users with the
		// ROLE_AUTHOR role to create atom entries in any collection
		 
		PostMethod method = new PostMethod(collectionUri);
		
		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry</atom:title><atom:summary>this is a test</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "arthur" to be defined in the example
		// security config to be assigned the ROLE_AUTHOR role
		
		int result = executeMethod(method, "arthur", "test");
		
		assertEquals(201, result);
		
	}
	
	
	
	
	public void testUserWithoutAuthorRoleCannotCreateAtomEntries() {
		
		String collectionUri = createRandomCollection("adam", "test");

		// we expect the default collection ACL to allow only users with the
		// ROLE_AUTHOR role to create atom entries in any collection
		 
		PostMethod method = new PostMethod(collectionUri);
		
		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry</atom:title><atom:summary>this is a test</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "rebecca" to be defined in the example
		// security config but NOT to be assigned the ROLE_AUTHOR role
		
		int result = executeMethod(method, "rebecca", "test");
		
		assertEquals(403, result);
		
	}
	
	
	
	
	public void testUserWithEditorRoleCanUpdateAtomEntries() {

		String collectionUri = createRandomCollection("adam", "test");
		
		String entryUri = createTestEntry("arthur", "test", collectionUri);

		// we expect the default collection ACL to allow only users with the
		// ROLE_EDITOR role to update any atom entries in any collection

		PutMethod method = new PutMethod(entryUri);

		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry - Edited</atom:title><atom:summary>this is a test, edited</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "edwina" to be defined in the example
		// security config to be assigned the ROLE_EDITOR role
		
		int result = executeMethod(method, "edwina", "test");
		
		assertEquals(200, result);

	}
	
	
	
	
	public void testUserWithoutEditorRoleCannotUpdateAtomEntries() {

		String collectionUri = createRandomCollection("adam", "test");
		
		String entryUri = createTestEntry("arthur", "test", collectionUri);

		// we expect the default collection ACL to allow only users with the
		// ROLE_EDITOR role to update any atom entries in any collection

		PutMethod method = new PutMethod(entryUri);

		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry - Edited</atom:title><atom:summary>this is a test, edited</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "rebecca" to be defined in the example
		// security config but NOT to be assigned the ROLE_EDITOR role
		
		int result = executeMethod(method, "rebecca", "test");
		
		assertEquals(403, result);

	}
	
	
	
}



