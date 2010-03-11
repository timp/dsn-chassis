package org.cggh.chassis.spike.atomserver;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import static org.cggh.chassis.spike.atomserver.AtomTestUtils.*;

import junit.framework.TestCase;




public class TestAtomProtocolWithDefaultSecurity extends TestCase {


	
	
	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";

	
	
	
 	public TestAtomProtocolWithDefaultSecurity() {

		// need to run install script once for example setup
		
		String installUrl = "http://localhost:8081/atomserver/atomserver/admin/install-example.xql";
		
		GetMethod method = new GetMethod(installUrl);
		
		int result = executeMethod(method, "adam", "test");
		
		if (result != 200) {
			throw new RuntimeException("installation failed: "+result);
		}
	
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
		
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

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
		
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

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
		
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

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
		
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

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

		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");
		
		String entryUri = createTestEntryAndReturnLocation(collectionUri, "arthur", "test");

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

		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");
		
		String entryUri = createTestEntryAndReturnLocation(collectionUri, "arthur", "test");

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



