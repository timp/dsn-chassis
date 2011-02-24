package org.cggh.chassis.spike.atomserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class TestProtocol extends TestCase {


	
	
	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";

	
	
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	
	private void doPutFeedToCreateCollection(String collectionUri) {

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
	
	
	
	
	private String doPostEntryToCreateMember(String collectionUri) {
		
		// setup a new POST request
		
		PostMethod method = new PostMethod(collectionUri);

		// create the request entity
		
		RequestEntity entity = null;

		// the request body - an atom entry document
		String entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member</atom:title>" +
				"<atom:summary>This is a summary.</atom:summary>" +
			"</atom:entry>";

		String contentType = "application/atom+xml;type=entry";

		String charSet = "utf-8";
		
		try {

			entity = new StringRequestEntity(entryDoc, contentType, charSet);

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
		
		// expect the status code is 201 Created
		
		assertEquals(201, result);

		// expect the Location header is set with an absolute URI

		String responseLocation = method.getResponseHeader("Location").getValue();
		assertNotNull(responseLocation);
		
		assertTrue(responseLocation.startsWith("http://")); 
		// N.B. we shouldn't assume any more than this, because entry could have
		// a location anywhere
		
		// expect Content-Type header 
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));
		
		return responseLocation;

	}
	

	
	
	private Document doPostMediaToCreateMediaResource(
			String collectionUri ,
			InputStream content , 
			String contentType
	) {
		
		// setup a new POST request
		
		PostMethod method = new PostMethod(collectionUri);

		// create the request entity
		RequestEntity entity = null;
		try {
			
			entity = new InputStreamRequestEntity(content, content.available(), contentType);

		} catch (IOException e1) {

			e1.printStackTrace();
			fail(e1.getLocalizedMessage());

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
		
		// expect the status code is 201 Created
		
		assertEquals(201, result);

		// expect the Location header is set with an absolute URI

		String responseLocation = method.getResponseHeader("Location").getValue();
		assertNotNull(responseLocation);
		
		assertTrue(responseLocation.startsWith("http://")); 
		// N.B. we shouldn't assume any more than this, because entry could have
		// a location anywhere
		
		// expect Content-Type header 
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		try {
		
			DocumentBuilder builder = factory.newDocumentBuilder();
			assertTrue(builder.isNamespaceAware());
			
			Document doc = builder.parse(method.getResponseBodyAsStream());
			assertNotNull(doc);
			
			return doc;

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (SAXException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		return null;
		
	}

	
	
	/**
	 * Test creation of a new atom collection by a PUT request with an atom feed
	 * document as the request body. N.B. this operation is not part of the
	 * standard atom protocol, but is an extension to support bootstrapping
	 * of an atom workspace, e.g., by an administrator.
	 */
	public void testPutFeedToCreateCollection() {
		
		// we want to test that we can create a new collection by PUT of atom 
		// feed doc however, we may run this test several times without cleaning
		// the database so we have to generate a random collection URI
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

	}
	
	
	
	/**
	 * Test creation of a new atom collection by a PUT request with an atom feed
	 * document as the request body, where the atom collection is nested within
	 * another collection, e.g. "/foo/bar". 
	 */
	public void testPutFeedToCreateNestedCollection() {
		
		// we want to test that we can create a new collection by PUT of atom 
		// feed doc however, we may run this test several times without cleaning
		// the database so we have to generate a random collection URI
		
		String collectionUri = SERVER_URI + Double.toString(Math.random()) + "/" + Double.toString(Math.random());

		doPutFeedToCreateCollection(collectionUri);

	}
	
	
	
	
	/**
	 * Test the use of a PUT request to update the feed metadata for an already
	 * existing atom collection.
	 */
	public void testPutFeedToCreateAndUpdateCollection() {
		
		// we need to create an atom collection first, to use as the target
		// for an attempt to update the feed metadata
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

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
		
		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// now create a new member by POSTing and atom entry document to the
		// collection URI
		
		doPostEntryToCreateMember(collectionUri);
		
	}



	
	/**
	 * Test the standard atom operation to update a member of a collection by
	 * a PUT request with an atom entry document as the request entity.
	 */
	public void testPutEntryToUpdateMember() {

		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// now create a new member by POSTing and atom entry document to the
		// collection URI
		
		String location = doPostEntryToCreateMember(collectionUri);

		// now put an updated entry document using a PUT request
		
		PutMethod method = new PutMethod(location);

		// create the request entity
		
		RequestEntity entity = null;

		// the request body - an atom entry document
		String entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member - Updated</atom:title>" +
				"<atom:summary>This is a summary, updated.</atom:summary>" +
			"</atom:entry>";

		String contentType = "application/atom+xml;type=entry";

		String charSet = "utf-8";
		
		try {

			entity = new StringRequestEntity(entryDoc, contentType, charSet);

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
	
	
	
	public void testPostTextDocumentToCreateMediaResource() {
		
		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// the request body - a plain text document

		String media = "This is a test.";
		ByteArrayInputStream content = null;

		try {
			
			content = new ByteArrayInputStream(media.getBytes("utf-8"));

		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
			fail(e1.getLocalizedMessage());

		}
		
		String contentType = "text/plain;charset=utf-8";
		
		// now create a new media resource by POSTing media to the collection URI
		
		doPostMediaToCreateMediaResource(collectionUri, content, contentType);

	}




	public void testPostSpreadsheetToCreateMediaResource() {
		
		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// now create a new media resource by POSTing media to the collection URI
		
		InputStream content = this.getClass().getClassLoader().getResourceAsStream("spreadsheet1.xls");
		String contentType = "application/vnd.ms-excel";
		
		doPostMediaToCreateMediaResource(collectionUri, content, contentType);

	}




	public void testPutTextDocumentToUpdateMediaResource() {
		
		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// the request body - a plain text document

		String media = "This is a test.";
		ByteArrayInputStream content = null;

		try {
			
			content = new ByteArrayInputStream(media.getBytes("utf-8"));

		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
			fail(e1.getLocalizedMessage());

		}
		
		String contentType = "text/plain;charset=utf-8";
		
		// now create a new media resource by POSTing media to the collection URI
		
		Document mediaLinkDoc = doPostMediaToCreateMediaResource(collectionUri, content, contentType);

		assertNotNull(mediaLinkDoc);
		
		NodeList links = mediaLinkDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link");
		
		assertEquals(3, links.getLength());

		String mediaLocation = null;
		
		for (int i=0; i<links.getLength(); i++) {
			Element e = (Element) links.item(i);
			String rel = e.getAttribute("rel");
			if (rel.equals("edit-media")) {
				mediaLocation = e.getAttribute("href");
			}
		}
		
		assertNotNull(mediaLocation);

		media = "This is a test - updated.";

		try {
			
			content = new ByteArrayInputStream(media.getBytes("utf-8"));

		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
			fail(e1.getLocalizedMessage());

		}
		
		// setup a new PUT request
		
		PutMethod method = new PutMethod(mediaLocation);

		// create the request entity
		RequestEntity entity = new InputStreamRequestEntity(content, content.available(), contentType);

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
		
		// expect the status code is 200 OK
		
		assertEquals(200, result);

		// expect no Location header 

		Header locationHeader = method.getResponseHeader("Location");
		assertNull(locationHeader);
		
		// expect Content-Type header 
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("text/plain"));

	}
	
	
	
	public void testGetFeed() {

		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// now try GET to collection URI
		
		GetMethod method = new GetMethod(collectionUri);

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
		
		// expect the status code is 200 OK
		
		assertEquals(200, result);

		// expect no Location header 

		Header locationHeader = method.getResponseHeader("Location");
		assertNull(locationHeader);
		
		// expect Content-Type header 
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));

	}
	
	
	
	
	public void testGetEntry() {

		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// now create a new member by POSTing and atom entry document to the
		// collection URI
		
		String location = doPostEntryToCreateMember(collectionUri);

		// now try GET to member URI
		
		GetMethod method = new GetMethod(location);

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
		
		// expect the status code is 200 OK
		
		assertEquals(200, result);

		// expect no Location header 

		Header locationHeader = method.getResponseHeader("Location");
		assertNull(locationHeader);
		
		// expect Content-Type header 
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));


	}
	
	
	
	public void testGetMedia() {

		// we need to create an atom collection first, to use as the target
		// for an attempt to create a new member
		
		String collectionUri = SERVER_URI + Double.toString(Math.random());
		
		doPutFeedToCreateCollection(collectionUri);

		// the request body - a plain text document

		String media = "This is a test.";
		ByteArrayInputStream content = null;

		try {
			
			content = new ByteArrayInputStream(media.getBytes("utf-8"));

		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
			fail(e1.getLocalizedMessage());

		}
		
		String contentType = "text/plain;charset=utf-8";
		
		// now create a new media resource by POSTing media to the collection URI
		
		Document mediaLinkDoc = doPostMediaToCreateMediaResource(collectionUri, content, contentType);

		assertNotNull(mediaLinkDoc);
		
		NodeList links = mediaLinkDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link");
		
		assertEquals(3, links.getLength());

		String mediaLocation = null;
		
		for (int i=0; i<links.getLength(); i++) {
			Element e = (Element) links.item(i);
			String rel = e.getAttribute("rel");
			if (rel.equals("edit-media")) {
				mediaLocation = e.getAttribute("href");
			}
		}
		
		assertNotNull(mediaLocation);
		
		// now try get on media location
		
		GetMethod method = new GetMethod(mediaLocation);

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
		
		// expect the status code is 200 OK
		
		assertEquals(200, result);

		// expect no Location header 

		Header locationHeader = method.getResponseHeader("Location");
		assertNull(locationHeader);
		
		// expect Content-Type header 
		
		String responseContentType = method.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("text/plain"));

	}
	
}


