package org.cggh.chassis.wwarn.ui.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Test that exist behaves as expected and that our restriction to disallow 
 * puts to non-atom entries is in place. 
 * 
 * @author Tim.Pizey
 * @since 2010/02/22
 * 
 */
public class AtomAuthorisationTestCase extends TestCase {

	static String serviceContext = "chassis-wwarn-ui";
	static String atomEditUrlFragment = "/" + serviceContext + "/atombeat";
	static String[] collections = { 
		    atomEditUrlFragment + "/content/studies",
			atomEditUrlFragment + "/content/submissions",
			atomEditUrlFragment + "/content/reviews",
			atomEditUrlFragment + "/content/derivations",
			atomEditUrlFragment + "/content/media" };

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
	
	protected final static String ALICE = "alice@example.org";
	protected final static String PASSWORD = "bar";
	
	
    protected String url(String relativeUrl) {
		return "http://localhost:8080" + relativeUrl;
	}
	protected String relativeUrl(String path) { 
		return "/" + serviceContext + path;
	}
	protected String contextUrl(String path) { 
		return url(relativeUrl(path));
	}
	
	/** Currently collections may be deleted, so need to be re-created. */  
	protected static void ensureCollectionAccessible(String url) throws Exception { 
		HttpURLConnection connection = getConnection(url);
		authorize(connection, ALICE, PASSWORD);
		connection.setRequestMethod("GET");
		connection.connect();
		int getStatus = connection.getResponseCode();
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
			
			assertEquals(collection, HttpURLConnection.HTTP_BAD_REQUEST, status);
		}
	}

	public void testCanCreateAndModifyAtomEntries() throws Exception {
		createAndModify("application/atom+xml", HttpURLConnection.HTTP_OK, ATOM_ENTRY);
	}
	
	public void testBobCannotGetEntityCreatedByAlice() throws Exception { 
		for (String collection : collections) {
			Tuple t = createEntry(collection,"application/atom+xml", ATOM_ENTRY);
			System.err.println(t.getUrl());
			assertEquals(HttpServletResponse.SC_FORBIDDEN, 
					getResourceAs(t.getUrl(),"application/atom+xml","bob@example.org", "bar"));
		}
	}
	public void testAliceCanGetEntityCreatedByAlice() throws Exception { 
		for (String collection : collections) {
			Tuple t = createEntry(collection,"application/atom+xml", ATOM_ENTRY);
			assertEquals(HttpServletResponse.SC_OK, 
					getResourceAs(t.getUrl(),"application/atom+xml",ALICE, PASSWORD));
		}
	}

	
	protected int getResourceAs(String entryUrl, String contentType, String user, String password) throws Exception {
		return accessResourceAs("GET", entryUrl, contentType, user, password);
	}
	protected int accessResourceAs(String method, String entryUrl, String contentType, String user, String password) throws Exception {
		if (!entryUrl.startsWith("http"))
			entryUrl = url(relativeUrl(entryUrl));
		HttpURLConnection connection = getConnection(entryUrl);
		authorize(connection, user, password);
		connection.setRequestMethod(method);
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
            
            System.err.println("Created:" + entryUrl);
            
			HttpURLConnection putConnection = getConnection(entryUrl);
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

		String entryUrl =  postConnection.getHeaderField("Location");
		System.err.println("createEntry: " + entryUrl);
		assertNotNull("No location returned from POST to collection "
				+ collection, entryUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
		String line;
		String responsePage = "";
		while ((line = in.readLine()) != null) 
			responsePage += line;
		in.close();
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
		connection.setRequestProperty("Authorization", "Basic "
				+ encodedAuthorisationValue);
	}
	
	public static int createCollection(String collectionUrl, String content) throws Exception {
        HttpURLConnection connection = getConnection(collectionUrl);
        
        authorize(connection, ALICE, PASSWORD);
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
