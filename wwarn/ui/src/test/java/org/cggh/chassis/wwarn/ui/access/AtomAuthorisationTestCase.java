package org.cggh.chassis.wwarn.ui.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Test that exist behaves as expected and that our restriction to disallow 
 * puts to non-atom entries is in place. 
 * 
 * @author Tim.Pizey
 * @since 2010/02/22
 * 
 */
public class AtomAuthorisationTestCase extends TestCase {

	static String existServiceContext = "chassis-wwarn-ui";
	static String atomEditUrlFragment = "/" + existServiceContext
			+ "/atom";
	static String[] collections = { 
		    atomEditUrlFragment + "/edit/studies",
			atomEditUrlFragment + "/edit/submissions",
			atomEditUrlFragment + "/edit/reviews",
			atomEditUrlFragment + "/edit/derivations",
			atomEditUrlFragment + "/edit/media" };

	private final static String ATOM_FEED = 
		"<?xml version='1.0' encoding='UTF-8'?>" +
		"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<title>An Atomic Feed</title>" + 
		"</atom:feed>" + 
		"\n";
	private final static String ATOM_ENTRY = 	
		"<?xml version='1.0' encoding='UTF-8'?>" + 
		"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<atom:title>An Atomic Entry</atom:title>" + 
        "<atom:author>" + 
        "<atom:email>alice@example.org</atom:email>" +
        "</atom:author>" + 
		"</atom:entry>" + 
		"\n";
	
	private final static String ALICE = "alice@example.org";
	private final static String PASSWORD = "bar";
	
	
    protected String url(String relativeUrl) {
		return "http://localhost:8081" + relativeUrl;
	}
	
	/** Currently collections may be deleted, so need to be re-created. */  
	protected static void ensureCollectionAccessible(String url) throws Exception { 
		HttpURLConnection connection = getConnection(url);
		authorize(connection, ALICE, PASSWORD);
		connection.setRequestMethod("GET");
		connection.connect();
		int getStatus = connection.getResponseCode();
		System.err.println("Response:" + getStatus);
		if (getStatus == 404) {
			int createStatus = createCollection(url, ATOM_FEED); 
			if (createStatus != 204)
				fail("Unexpected response code " + createStatus + " for " + url);
		} else if (getStatus != 200)
			fail("Unexpected response code " + getStatus + " for " + url);

	}
	
	/** Just asserts that Atom protocol holds: you are not allowed to PUT to collections. */
	public void testCannotPutToCollections() throws Exception {
		for (String collection : collections) {
			ensureCollectionAccessible(url(collection));
			HttpURLConnection connection = getConnection(url(collection));
			authorize(connection, ALICE, PASSWORD);
			connection.setRequestMethod("PUT");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/atom+xml");
			Writer writer = new OutputStreamWriter(connection
					.getOutputStream(), "UTF-8");
			writer.write(ATOM_ENTRY);
			writer.close();
			connection.connect();
			int status = connection.getResponseCode();
			assertEquals(collection + " response " + status, HttpURLConnection.HTTP_BAD_REQUEST, status);
		}
	}

	public void testCanPutToAtomEntries() throws Exception {
		createAndModify("application/atom+xml", HttpURLConnection.HTTP_OK, ATOM_ENTRY);
	}
	public void testCannotPutToNonAtomEntries() throws Exception {
		createAndModify("text/html", HttpURLConnection.HTTP_UNSUPPORTED_TYPE, ATOM_ENTRY);
		createAndModify("text/plain", HttpURLConnection.HTTP_UNSUPPORTED_TYPE, "This is not atom speak\n");
	}

	public void testBobCannotGetEntityCreatedByAlice() throws Exception { 
		for (String collection : collections) {
			Tuple t = createEntry(collection,"application/atom+xml", ATOM_ENTRY);
			assertEquals(HttpServletResponse.SC_UNAUTHORIZED, 
					requestResourceAs(t.getUrl(),"application/atom+xml","bob@example.org", "bar"));
		}
	}
	public void testAliceCanGetEntityCreatedByAlice() throws Exception { 
		for (String collection : collections) {
			Tuple t = createEntry(collection,"application/atom+xml", ATOM_ENTRY);
			assertEquals(HttpServletResponse.SC_OK, 
					requestResourceAs(t.getUrl(),"application/atom+xml",ALICE, PASSWORD));
		}
	}

	private int requestResourceAs(String entryUrl, String contentType, String user, String password) throws Exception {
		HttpURLConnection connection = getConnection(url(entryUrl));
		authorize(connection, user, password);
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", contentType);
		connection.connect();
		return connection.getResponseCode();
	}

	private void createAndModify(String contentType, int expectedStatus, String content) 
			throws Exception, ProtocolException, UnsupportedEncodingException, IOException {
		for (String collection : collections) {
			
			ensureCollectionAccessible(url(collection));

            Tuple t = createEntry(collection,contentType, content);
            String entryUrl = t.url;
    		System.out.println(t.url);
    		System.out.println(t.content);

			HttpURLConnection putConnection = getConnection(url(entryUrl));
			authorize(putConnection, ALICE, PASSWORD);
			putConnection.setRequestMethod("PUT");
			putConnection.setDoOutput(true);
			putConnection.setRequestProperty("Content-Type", contentType);
			Writer putWriter = new OutputStreamWriter(putConnection.getOutputStream(), "UTF-8");
			putWriter.write(content);
			putWriter.close();
			putConnection.connect();
			assertEquals("Server returned response code for " + entryUrl,
						expectedStatus, putConnection.getResponseCode());
			
			String editMediaLink = collection + "/" + getMediaEditLink(t.content);
			System.err.println("--- Putting document against " + editMediaLink
					+ " - should work ---");
			HttpURLConnection putMediaConnection = getConnection(url(editMediaLink));
			authorize(putMediaConnection, ALICE, PASSWORD);
			putMediaConnection.setRequestMethod("PUT");
			putMediaConnection.setDoOutput(true);
			putMediaConnection.setRequestProperty("Content-Type", contentType);
			Writer putMediaWriter = new OutputStreamWriter(putMediaConnection.getOutputStream(), "UTF-8");
			putMediaWriter.write(content);
			putMediaWriter.close();
			putMediaConnection.connect();
			assertEquals("Response code for " + editMediaLink,
						expectedStatus, putMediaConnection.getResponseCode());
		}
	}

	private Tuple createEntry(String collection, String contentType, String content) throws Exception { 
		HttpURLConnection postConnection = getConnection(url(collection));
		authorize(postConnection, ALICE, PASSWORD);
		postConnection.setRequestMethod("POST");
		postConnection.setDoOutput(true);
		postConnection.setRequestProperty("Content-Type", contentType);
		Writer writer = new OutputStreamWriter(postConnection
				.getOutputStream(), "UTF-8");
		writer.write(content);
		writer.close();
		postConnection.connect();
		int postStatus = postConnection.getResponseCode();
		assertEquals("Server returned response code for " + collection,
				HttpURLConnection.HTTP_CREATED, postStatus);

		String entryUrl = atomEditUrlFragment + postConnection.getHeaderField("Location");
		assertNotNull("No location returned from POST to collection "
				+ collection, entryUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
		String line;
		String responsePage = "";
		while ((line = in.readLine()) != null) 
			responsePage += line;
		in.close();
		System.out.println(responsePage);
		return new Tuple(entryUrl, responsePage);
	}
	protected static HttpURLConnection getConnection(String url) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.addRequestProperty("Accept", "text/plain *; q=.2, */*; q=.2");
		return connection;
	}

	private static void authorize(HttpURLConnection connection, String username,
			String password) {
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((username + ":" + password).getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		connection.setRequestProperty("Authorization", "Basic "
				+ encodedAuthorisationValue);
	}
	
	public static int createCollection(String collectionUrl, String content) throws Exception {
        HttpURLConnection connection = getConnection(collectionUrl);
        
		connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("Accept", "application/atom+xml,application/xml");
        connection.setRequestProperty("Content-Type", "application/atom+xml;type=feed;charset=\"utf-8\"");
        connection.setRequestProperty("Content-Length", Integer.toString(content.length()));
		
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(content);
        out.close();

        return connection.getResponseCode();
	}	


	String getMediaEditLink(String atomEntry) throws Exception { 
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(atomEntry));
		Document doc = db.parse(inStream);

		doc.getDocumentElement().normalize();
		NodeList linkNodes = doc.getElementsByTagName("link");
		for (int s = 0; s < linkNodes.getLength(); s++) {
			Node linkNode = linkNodes.item(s);
			Element linkElement = (Element)linkNode;
			if (linkElement.getAttribute("edit-media") != null) { 
				String href = linkElement.getAttribute("href");
				if (href == null)
					throw new RuntimeException("No edit-media link href value found");
				return href;
			}
		}
		throw new RuntimeException("No edit-media link ");
	}

	
	class Tuple {
		String url;
		String content;
		Tuple(String url, String content) { 
			this.url = url;
			this.content = content;
		}
		public String getUrl() {
			return url;
		}
		public String getContent() {
			return content;
		}		
	}
}
