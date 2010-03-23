package org.cggh.chassis.spike.atomserver;



import static org.cggh.chassis.spike.atomserver.AtomTestUtils.*;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;




public class TestAclProtocol extends TestCase {


	
	
	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";
	private static final String ACL_URI = "http://localhost:8081/atomserver/atomserver/acl/";

	
	

	public TestAclProtocol() {
	
	
	}
	
	
	
	
	protected void setUp() throws Exception {
		super.setUp();

		// need to run install before each test to ensure default global acl is restored
		
		String installUrl = "http://localhost:8081/atomserver/atomserver/admin/install-example.xql";
		
		GetMethod method = new GetMethod(installUrl);
		
		int result = executeMethod(method, "adam", "test");
		
		if (result != 200) {
			throw new RuntimeException("installation failed: "+result);
		}
		
	}
	
	
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	

	public void testGetGlobalAcl() {
		
		GetMethod g = new GetMethod(ACL_URI);
		int r = executeMethod(g, "adam", "test");

		assertEquals(200, r);
		
		verifyAtomResponse(g);
		
		Document d = getResponseBodyAsDocument(g);
		verifyDocIsAtomEntryWithAclContent(d);
		
	}
	
	
	
	public void testGetGlobalAclDenied() {

		GetMethod g = new GetMethod(ACL_URI);
		int r = executeMethod(g, "rebecca", "test");

		assertEquals(403, r);

	}
	

	
	public void testGetCollectionAcl() {

		// set up test by creating a collection
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

		// retrieve collection feed
		GetMethod g = new GetMethod(collectionUri);
		int r = executeMethod(g, "adam", "test");
		assertEquals(200, r);
		
		// look for "edit-acl" link
		Document d = getResponseBodyAsDocument(g);
		String aclLocation = getLinkHref(d, "edit-acl");
		assertNotNull(aclLocation);
		
		// make a second get request for the acl
		GetMethod h = new GetMethod(aclLocation);
		int s = executeMethod(h, "adam", "test");
		assertEquals(200, s);
		verifyAtomResponse(h);
		Document e = getResponseBodyAsDocument(h);
		verifyDocIsAtomEntryWithAclContent(e);

	}
	
	
	
	public void testGetCollectionNoEditAclLink() {

		// set up test by creating a collection
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

		// retrieve collection feed
		GetMethod g = new GetMethod(collectionUri);
		int r = executeMethod(g, "rebecca", "test");
		assertEquals(200, r);
		
		// look for "edit-acl" link
		Document d = getResponseBodyAsDocument(g);
		String aclLocation = getLinkHref(d, "edit-acl");
		assertNull(aclLocation);
		
	}
	
	
	
	public void testGetCollectionAclDenied() {

		// set up test by creating a collection
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

		// retrieve collection feed
		GetMethod g = new GetMethod(collectionUri);
		int r = executeMethod(g, "adam", "test");
		assertEquals(200, r);
		
		// look for "edit-acl" link
		Document d = getResponseBodyAsDocument(g);
		String aclLocation = getLinkHref(d, "edit-acl");
		assertNotNull(aclLocation);
		
		// make a second get request for the acl
		GetMethod h = new GetMethod(aclLocation);
		int s = executeMethod(h, "rebecca", "test");
		assertEquals(403, s);

	}
	
	
	
	public void testGetMemberAcl() {

		// set up test by creating a collection
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");
		String memberUri = createTestEntryAndReturnLocation(collectionUri, "audrey", "test");

		// retrieve member entry
		GetMethod g = new GetMethod(memberUri);
		int r = executeMethod(g, "audrey", "test");
		assertEquals(200, r);
		
		// look for "edit-acl" link
		Document d = getResponseBodyAsDocument(g);
		String aclLocation = getLinkHref(d, "edit-acl");
		assertNotNull(aclLocation);
		
		// make a second get request for the acl
		GetMethod h = new GetMethod(aclLocation);
		int s = executeMethod(h, "adam", "test");
		assertEquals(200, s);
		verifyAtomResponse(h);
		Document e = getResponseBodyAsDocument(h);
		verifyDocIsAtomEntryWithAclContent(e);

	}
	
	
	

	public void testGetMemberNoEditAclLink() {

		// set up test by creating a collection
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");
		String memberUri = createTestEntryAndReturnLocation(collectionUri, "audrey", "test");

		// retrieve member entry
		GetMethod g = new GetMethod(memberUri);
		int r = executeMethod(g, "rebecca", "test");
		assertEquals(200, r);
		
		// look for "edit-acl" link
		Document d = getResponseBodyAsDocument(g);
		String aclLocation = getLinkHref(d, "edit-acl");
		assertNull(aclLocation);
		
	}
	
	
	
	public void testGetMemberAclDenied() {

		// set up test by creating a collection
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");
		String memberUri = createTestEntryAndReturnLocation(collectionUri, "audrey", "test");

		// retrieve member entry
		GetMethod g = new GetMethod(memberUri);
		int r = executeMethod(g, "audrey", "test");
		assertEquals(200, r);
		
		// look for "edit-acl" link
		Document d = getResponseBodyAsDocument(g);
		String aclLocation = getLinkHref(d, "edit-acl");
		assertNotNull(aclLocation);
		
		// make a second get request for the acl
		GetMethod h = new GetMethod(aclLocation);
		int s = executeMethod(h, "rebecca", "test");
		assertEquals(403, s);

	}
	
	
	
	public void testUpdateGlobalAcl() {
		
		// make sure adam can create collections
		String u = createTestCollection(SERVER_URI, "adam", "test");
		assertNotNull(u);

		// strip global acls
		String content = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
			"<atom:content type=\"application/vnd.atombeat+xml\">" +
			"<acl xmlns=\"\"></acl>" +
			"</atom:content>" +
			"</atom:entry>";
		
		PutMethod p = new PutMethod(ACL_URI);
		setAtomRequestEntity(p, content);
		int r = executeMethod(p, "adam", "test");
		assertEquals(200, r);
		verifyAtomResponse(p);
		Document e = getResponseBodyAsDocument(p);
		verifyDocIsAtomEntryWithAclContent(e);
		
		// now try to create a collection
		String v = createTestCollection(SERVER_URI, "adam", "test");
		assertNull(v);
		 
	}
	
	
	
	public void testUpdateGlobalAclDenied() {
		
		// try to update global acls
		String content = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
			"<atom:content type=\"application/vnd.atombeat+xml\">" +
			"<acl xmlns=\"\"></acl>" +
			"</atom:content>" +
			"</atom:entry>";
		
		PutMethod p = new PutMethod(ACL_URI);
		setAtomRequestEntity(p, content);
		int r = executeMethod(p, "rebecca", "test");
		assertEquals(403, r);
		
	}
	
	
	
	public void testUpdateCollectionAcl() {

		// set up test by creating a collection
		String collectionUri = createTestCollection(SERVER_URI, "adam", "test");

		// retrieve collection feed
		GetMethod g = new GetMethod(collectionUri);
		int r = executeMethod(g, "rebecca", "test");
		assertEquals(200, r);
		
		// look for "edit-acl" link
		Document d = getResponseBodyAsDocument(g);
		String aclLocation = getLinkHref(d, "edit-acl");
		assertNotNull(aclLocation);
		
		// update the acl
		PutMethod p = new PutMethod(aclLocation);
		String content = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
			"<atom:content type=\"application/vnd.atombeat+xml\">" +
			"<acl xmlns=\"\"></acl>" +
			"</atom:content>" +
			"</atom:entry>";
		setAtomRequestEntity(p, content);
		int s = executeMethod(p, "adam", "test");
		assertEquals(200, s);
		verifyAtomResponse(p);
		Document e = getResponseBodyAsDocument(p);
		verifyDocIsAtomEntryWithAclContent(e);

		// retrieve collection feed again
		GetMethod h = new GetMethod(collectionUri);
		int t = executeMethod(h, "rebecca", "test");
		assertEquals(403, t);
		
	}
	
	
	
	// TODO get and put media acls
	
	private static String verifyAtomResponse(HttpMethod m) {

		String h = m.getResponseHeader("Content-Type").getValue();
		assertNotNull(h);
		assertTrue(h.startsWith("application/atom+xml"));
		return h;
		
	}
	
	
	
	
	private static Element verifyDocIsAtomEntryWithAclContent(Document d) {

		// verify root element is atom entry
		Element e = d.getDocumentElement();
		assertEquals("entry", e.getLocalName());
		assertEquals("http://www.w3.org/2005/Atom", e.getNamespaceURI());
		
		// verify atom content element is present
		Element c = (Element) e.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "content").item(0);
		assertNotNull(c);
		
		// verify acl content
		NodeList n = c.getElementsByTagNameNS("", "acl");
		Element a = (Element) n.item(0);
		assertNotNull(a);
		
		return a;
		
	}
	
	
	
}



