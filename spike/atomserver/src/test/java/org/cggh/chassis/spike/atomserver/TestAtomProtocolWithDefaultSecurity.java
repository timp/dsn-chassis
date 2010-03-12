package org.cggh.chassis.spike.atomserver;

import java.io.InputStream;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import static org.cggh.chassis.spike.atomserver.AtomTestUtils.*;

import junit.framework.TestCase;




public class TestAtomProtocolWithDefaultSecurity extends TestCase {


	
	
	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";

	
	
	private String testCollectionUri = null;
	
	
	
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
		testCollectionUri = createTestCollection(SERVER_URI, "adam", "test");
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
		
		// we expect the default collection ACL to allow users with the
		// ROLE_AUTHOR role to create atom entries in any collection
		 
		PostMethod method = new PostMethod(testCollectionUri);
		
		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry</atom:title><atom:summary>this is a test</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "austin" to be defined in the example
		// security config to be assigned the ROLE_AUTHOR role
		
		int result = executeMethod(method, "austin", "test");
		
		assertEquals(201, result);
		
	}
	
	
	
	
	public void testUserWithoutAuthorRoleCannotCreateAtomEntries() {
		
		// we expect the default collection ACL to allow only users with the
		// ROLE_AUTHOR role to create atom entries in any collection
		 
		PostMethod method = new PostMethod(testCollectionUri);
		
		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry</atom:title><atom:summary>this is a test</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the user "rebecca" to be defined in the example
		// security config but NOT to be assigned the ROLE_AUTHOR role
		
		int result = executeMethod(method, "rebecca", "test");
		
		assertEquals(403, result);
		
	}
	
	
	
	
	public void testUserWithEditorRoleCanUpdateAtomEntries() {

		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "austin", "test");

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

		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "austin", "test");

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
	
	
	
	public void testAuthorCanUpdateTheirOwnEntry() {

		// we expect "audrey" is assigned the ROLE_AUTHOR so can create entries
		
		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");

		PutMethod method = new PutMethod(entryUri);

		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry - Edited</atom:title><atom:summary>this is a test, edited</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the default resource ACL to allow users to update entries
		// they created

		int result = executeMethod(method, "audrey", "test");
		
		assertEquals(200, result);

	}



	public void testAuthorCannotUpdateAnotherAuthorsEntry() {

		// we expect "audrey" is assigned the ROLE_AUTHOR so can create entries
		
		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");

		PutMethod method = new PutMethod(entryUri);

		String content = "<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Entry - Edited</atom:title><atom:summary>this is a test, edited</atom:summary></atom:entry>";
		setAtomRequestEntity(method, content);
		
		// we expect the default resource ACL to allow users to update entries
		// they created, but not to allow them to update other authors' entries

		int result = executeMethod(method, "austin", "test");
		
		assertEquals(403, result);

	}
	
	
	
	public void testUserWithReaderRoleCanListCollections() {

		// setup test
		createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");
		
		// list collection, expecting user "rebecca" to be in role ROLE_READER
		GetMethod method = new GetMethod(testCollectionUri);
		int result = executeMethod(method, "rebecca", "test");
		
		assertEquals(200, result);
		
	}
	
	
	
	public void testUserWithoutReaderRoleCannotListCollections() {

		// setup test
		createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");
		
		// list collection, expecting user "ursula" not to be in role ROLE_READER
		GetMethod method = new GetMethod(testCollectionUri);
		int result = executeMethod(method, "ursula", "test");
		
		assertEquals(403, result);
		
	}
	
	
	
	
	public void testUserWithReaderRoleCanRetrieveEntry() {
		
		// setup test
		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");
		
		// retrieve entry, expecting user "rebecca" to be in role ROLE_READER
		GetMethod method = new GetMethod(entryUri);
		int result = executeMethod(method, "rebecca", "test");
		
		assertEquals(200, result);
	}
	
	
	
	public void testUserWithoutReaderRoleCannotRetrieveEntry() {
		
		// setup test
		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");
		
		// list collection, expecting user "ursula" not to be in role ROLE_READER
		GetMethod method = new GetMethod(entryUri);
		int result = executeMethod(method, "ursula", "test");
		
		assertEquals(403, result);

	}
	
	
	
	public void testUserWithAuthorRoleCanCreateMedia() {
		
		PostMethod method = new PostMethod(testCollectionUri);
		String media = "This is a test.";
		setTextPlainRequestEntity(method, media);
		int result = executeMethod(method, "audrey", "test");
		
		// we expect the user "audrey" to be defined in the example
		// security config to be assigned the ROLE_AUTHOR role
		
		assertEquals(201, result);

	}



	public void testUserWithoutAuthorRoleCannotCreateMedia() {
		
		PostMethod method = new PostMethod(testCollectionUri);
		String media = "This is a test.";
		setTextPlainRequestEntity(method, media);
		int result = executeMethod(method, "rebecca", "test");
		
		// we expect the user "rebecca" to be defined in the example
		// security config to not be assigned the ROLE_AUTHOR role
		
		assertEquals(403, result);

	}



	public void testUserWithDataAuthorRoleCanCreateMediaWithSpecificMediaType() {
		
		PostMethod method = new PostMethod(testCollectionUri);
		InputStream content = this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
		String contentType = "application/vnd.ms-excel";
		setInputStreamRequestEntity(method, content, contentType);
		int result = executeMethod(method, "daniel", "test");
		
		// we expect the user "daniel" to be defined in the example
		// security config to be assigned the ROLE_DATA_AUTHOR role
		
		assertEquals(201, result);

	}



	public void testUserWithDataAuthorRoleCannotCreateMediaWithSpecificMediaType() {
		
		PostMethod method = new PostMethod(testCollectionUri);
		String media = "This is a test.";
		setTextPlainRequestEntity(method, media);
		int result = executeMethod(method, "daniel", "test");
		
		// we expect the user "daniel" to be defined in the example
		// security config to be assigned the ROLE_DATA_AUTHOR role
		
		assertEquals(403, result);

	}
	
	
	
	public void testAuthorsCanRetrieveEntryTheyCreated() {
		
		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");

		GetMethod method = new GetMethod(entryUri);
		
		// we expect the default resource ACL to allow users to retrieve entries
		// they created

		int result = executeMethod(method, "audrey", "test");
		
		assertEquals(200, result);

	}



	public void testAuthorsCannotRetrieveAnotherAuthorsEntry() {
		
		String entryUri = createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");

		GetMethod method = new GetMethod(entryUri);
		
		// we expect the default resource ACL to allow users to retrieve entries
		// they created, but not to allow them to retrieve other authors' entries

		int result = executeMethod(method, "austin", "test");
		
		assertEquals(403, result);

	}
	
	
	
	public void testAuthorsCanListCollectionButOnlyRetrieveEntriesTheyCreated() {

		createTestEntryAndReturnLocation(testCollectionUri, "audrey", "test");
		createTestEntryAndReturnLocation(testCollectionUri, "austin", "test");
		
		GetMethod method = new GetMethod(testCollectionUri);
		int result = executeMethod(method, "audrey", "test");
		
		assertEquals(200, result);

		Document feedDoc = getResponseBodyAsDocument(method);
		
		NodeList entries = feedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
		assertEquals(1, entries.getLength());
		
		method = new GetMethod(testCollectionUri);
		result = executeMethod(method, "austin", "test");
		
		assertEquals(200, result);

		feedDoc = getResponseBodyAsDocument(method);
		
		entries = feedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
		assertEquals(1, entries.getLength());
		
		// but readers should be able to retrieve all entries
		
		method = new GetMethod(testCollectionUri);
		result = executeMethod(method, "rebecca", "test");
		
		assertEquals(200, result);

		feedDoc = getResponseBodyAsDocument(method);
		
		entries = feedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
		assertEquals(2, entries.getLength());
		
	}


	
	// TODO test retrieving and updating media resources and media-link entries
	
	// TODO test creating media resources with multipart requests
	
	// TODO test creating collections with legacy POST requests

}



