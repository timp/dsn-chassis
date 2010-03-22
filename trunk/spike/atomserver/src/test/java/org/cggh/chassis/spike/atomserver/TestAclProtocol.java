package org.cggh.chassis.spike.atomserver;



import static org.cggh.chassis.spike.atomserver.AtomTestUtils.*;

import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import junit.framework.TestCase;




public class TestAclProtocol extends TestCase {


	
	
	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";
	private static final String ACL_URI = "http://localhost:8081/atomserver/atomserver/acl/";

	
	

	public TestAclProtocol() {
	
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

	

	public void testGetGlobalAcl() {
		
		GetMethod g = new GetMethod(ACL_URI);
		int r = executeMethod(g, "adam", "test");

		// verify reponse code
		assertEquals(200, r);
		
		// verify response content type
		String h = g.getResponseHeader("Content-Type").getValue();
		assertNotNull(h);
		assertTrue(h.startsWith("application/atom+xml"));
		
		Document d = getResponseBodyAsDocument(g);

		// verify root element is atom entry
		Element e = d.getDocumentElement();
		assertEquals("entry", e.getLocalName());
		assertEquals("http://www.w3.org/2005/Atom", e.getNamespaceURI());
		
		// verify atom content element is present
		Element c = (Element) e.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "content").item(0);
		assertNotNull(c);
		
		// verify acl content
		NodeList n = c.getChildNodes();
		assertEquals(1, n.getLength());
		Element a = (Element) n.item(0);
		assertEquals("acl", a.getLocalName());
		assertEquals("", a.getNamespaceURI());
	
	}
	
	
	
}



