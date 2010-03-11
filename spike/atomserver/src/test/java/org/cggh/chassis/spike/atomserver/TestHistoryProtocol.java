package org.cggh.chassis.spike.atomserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class TestHistoryProtocol extends TestCase {

	
	private static final String SERVER_URI = "http://localhost:8081/atomserver/atomserver/edit/";
	private static final String TEST_COLLECTION_URI = SERVER_URI + "test-history";
	private static DocumentBuilderFactory factory;
	private static DocumentBuilder builder;
	private static HttpClient client = new HttpClient();
	
	static {

		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		try {
		
			builder = factory.newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {

			e.printStackTrace();

		} 

	}

	private static final BasicScheme basic = new BasicScheme();
	
	
	
	private static void authenticate(HttpMethod method) {
		try {
			String authorization = basic.authenticate(new UsernamePasswordCredentials("adam", "test"), method);
			method.setRequestHeader("Authorization", authorization);
		} 
		catch (Throwable t) {
			t.printStackTrace();
			fail(t.getLocalizedMessage());
		}
	}
	
	

	
	
	public static void putFeedToCreateCollection(String collectionUri) {

		// setup a new PUT request
		
		PutMethod method = new PutMethod(collectionUri);
		authenticate(method);

		method.setRequestHeader("X-Atom-Enable-History", "true");
		
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
		
		// expect the status code is 201 CREATED or 200 OK if we ran this test before
		
		assertTrue((result == 200 || result == 201));

	}
	
	
	
	
	public static Document postEntry(String collectionUri, String entryDoc, Header[] headers) {
		
		// setup a new POST request
		
		PostMethod method = new PostMethod(collectionUri);
		authenticate(method);
		
		// add custom headers
		
		for (Header h : headers) {
			method.addRequestHeader(h);
		}
		
		// create the request entity
		
		RequestEntity entity = null;

		String contentType = "application/atom+xml;type=entry";

		String charSet = "utf-8";
		
		try {

			entity = new StringRequestEntity(entryDoc, contentType, charSet);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		method.setRequestEntity(entity);
		
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

		// now check for history link
		
		Document doc = null;
		
		try {
		
			doc = builder.parse(method.getResponseBodyAsStream());
			
		} catch (SAXException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}

		assertNotNull(doc);

		return doc;
	}
	
	
	
	
	public static Document putEntry(String uri, String entryDoc, Header[] headers) {
		
		// setup a new POST request
		
		PutMethod method = new PutMethod(uri);
		authenticate(method);
		
		// add custom headers
		
		for (Header h : headers) {
			method.addRequestHeader(h);
		}
		
		// create the request entity
		
		RequestEntity entity = null;

		String contentType = "application/atom+xml;type=entry";

		String charSet = "utf-8";
		
		try {

			entity = new StringRequestEntity(entryDoc, contentType, charSet);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		method.setRequestEntity(entity);
		
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
		
		// expect the status code is 200 
		
		assertEquals(200, result);

		// now check for history link
		
		Document doc = null;
		
		try {
		
			doc = builder.parse(method.getResponseBodyAsStream());
			
		} catch (SAXException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}

		assertNotNull(doc);

		return doc;
	}

	
	
	
	public void setUp() {
	
		putFeedToCreateCollection(TEST_COLLECTION_URI);
	
	}
	
	
	
	public void testEntryWithNoRevisions() {
		
		// the request body - an atom entry document
		String entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member - Will Not Be Revised</atom:title>" +
				"<atom:summary>This is a summary, will not be revised.</atom:summary>" +
			"</atom:entry>";

		Header[] headers = {
				new Header("X-Atom-Revision-Comment", "first draft")
		};
		
		Document doc = postEntry(TEST_COLLECTION_URI, entryDoc, headers);
		
		NodeList links = doc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link");
		
		String historyLocation = null;
		
		for (int i=0; i<links.getLength(); i++) {
			Element e = (Element) links.item(i);
			String rel = e.getAttribute("rel");
			if (rel.equals("history")) {
				historyLocation = e.getAttribute("href");
			}
		}
		
		assertNotNull(historyLocation);
		
		// now try to retrieve history
		
		GetMethod get = new GetMethod(historyLocation);
		authenticate(get);

		int result = -1;
		
		try {

			// make the HTTP request now
			result = client.executeMethod(get);

		} catch (HttpException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		// expect the status code is 200 OK
		
		assertEquals(200, result);

		// expect Content-Type header 
		
		String responseContentType = get.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));

		// now check content is feed
		
		Document historyFeedDoc = null;
		
		try {
		
			historyFeedDoc = builder.parse(get.getResponseBodyAsStream());
			
		} catch (SAXException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}

		assertNotNull(historyFeedDoc);

		// now check content is feed
		
		NodeList feedNodes = historyFeedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "feed");
		assertEquals(1, feedNodes.getLength());
		
		// check number of entries

		NodeList entryNodes = historyFeedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
		assertEquals(1, entryNodes.getLength());

	}



	
	public void testEntryWithOneRevision() {
		
		// the request body - an atom entry document
		String entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member</atom:title>" +
				"<atom:summary>This is a summary, first daft.</atom:summary>" +
			"</atom:entry>";

		Header[] headers = {
				new Header("X-Atom-Revision-Comment", "first draft")
		};
		
		Document initialDoc = postEntry(TEST_COLLECTION_URI, entryDoc, headers);
		
		NodeList links = initialDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link");
		
		String location = null;
		
		for (int i=0; i<links.getLength(); i++) {
			Element e = (Element) links.item(i);
			String rel = e.getAttribute("rel");
			if (rel.equals("edit")) {
				location = e.getAttribute("href");
			}
		}
		
		assertNotNull(location);
		
		entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member - Updated</atom:title>" +
				"<atom:summary>This is a summary, updated.</atom:summary>" +
			"</atom:entry>";

		Header[] headers2 = {
				new Header("X-Atom-Revision-Comment", "second draft")
		};
		
		Document updatedDoc = putEntry(location, entryDoc, headers2);
		
		links = updatedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link");
		
		String historyLocation = null;
		
		for (int i=0; i<links.getLength(); i++) {
			Element e = (Element) links.item(i);
			String rel = e.getAttribute("rel");
			if (rel.equals("history")) {
				historyLocation = e.getAttribute("href");
			}
		}
		
		assertNotNull(historyLocation);
		
		// now try to retrieve history
		
		GetMethod get = new GetMethod(historyLocation);
		authenticate(get);

		int result = -1;
		
		try {

			// make the HTTP request now
			result = client.executeMethod(get);

		} catch (HttpException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		// expect the status code is 200 OK
		
		assertEquals(200, result);

		// expect Content-Type header 
		
		String responseContentType = get.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));

		// now check content is feed
		
		Document historyFeedDoc = null;
		
		try {
		
			historyFeedDoc = builder.parse(get.getResponseBodyAsStream());
			
		} catch (SAXException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}

		assertNotNull(historyFeedDoc);

		// now check content is feed
		
		NodeList feedNodes = historyFeedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "feed");
		assertEquals(1, feedNodes.getLength());
		
		// check number of entries

		NodeList entryNodes = historyFeedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
		assertEquals(2, entryNodes.getLength());

	}



	public void testEntryWithTwoRevisions() {
		
		// the request body - an atom entry document
		String entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member</atom:title>" +
				"<atom:summary>This is a summary, first daft.</atom:summary>" +
			"</atom:entry>";

		Header[] headers = {
				new Header("X-Atom-Revision-Comment", "first draft")
		};
		
		Document initialDoc = postEntry(TEST_COLLECTION_URI, entryDoc, headers);
		
		NodeList links = initialDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link");
		
		String location = null;
		
		for (int i=0; i<links.getLength(); i++) {
			Element e = (Element) links.item(i);
			String rel = e.getAttribute("rel");
			if (rel.equals("edit")) {
				location = e.getAttribute("href");
			}
		}
		
		assertNotNull(location);
		
		entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member - Updated</atom:title>" +
				"<atom:summary>This is a summary, updated (second draft).</atom:summary>" +
			"</atom:entry>";

		Header[] headers2 = {
				new Header("X-Atom-Revision-Comment", "second draft")
		};
		
		@SuppressWarnings("unused")
		Document updatedDoc = putEntry(location, entryDoc, headers2);
		
		entryDoc = 
			"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
				"<atom:title>Test Member - Updated Again</atom:title>" +
				"<atom:summary>This is a summary, updated (third draft).</atom:summary>" +
			"</atom:entry>";

		Header[] headers3 = {
				new Header("X-Atom-Revision-Comment", "third draft")
		};
		
		Document updatedAgainDoc = putEntry(location, entryDoc, headers3);
		
		links = updatedAgainDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "link");
		
		String historyLocation = null;
		
		for (int i=0; i<links.getLength(); i++) {
			Element e = (Element) links.item(i);
			String rel = e.getAttribute("rel");
			if (rel.equals("history")) {
				historyLocation = e.getAttribute("href");
			}
		}
		
		assertNotNull(historyLocation);
		
		// now try to retrieve history
		
		GetMethod get = new GetMethod(historyLocation);
		authenticate(get);

		int result = -1;
		
		try {

			// make the HTTP request now
			result = client.executeMethod(get);

		} catch (HttpException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}
		
		// expect the status code is 200 OK
		
		assertEquals(200, result);

		// expect Content-Type header 
		
		String responseContentType = get.getResponseHeader("Content-Type").getValue();
		assertNotNull(responseContentType);
		assertTrue(responseContentType.trim().startsWith("application/atom+xml"));

		// now check content is feed
		
		Document historyFeedDoc = null;
		
		try {
		
			historyFeedDoc = builder.parse(get.getResponseBodyAsStream());
			
		} catch (SAXException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getLocalizedMessage());

		}

		assertNotNull(historyFeedDoc);

		// now check content is feed
		
		NodeList feedNodes = historyFeedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "feed");
		assertEquals(1, feedNodes.getLength());
		
		// check number of entries

		NodeList entryNodes = historyFeedDoc.getElementsByTagNameNS("http://www.w3.org/2005/Atom", "entry");
		assertEquals(3, entryNodes.getLength());

	}
}
