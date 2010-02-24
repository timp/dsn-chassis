package org.cggh.chassis.generic.client.htmlunit;

/*
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
*/
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import net.sourceforge.jwebunit.exception.TestingEngineResponseException;
import net.sourceforge.jwebunit.htmlunit.HtmlUnitTestingEngineImpl;
import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;

/**
 * @author Tim.Pizey
 * @since 01 December 2009
 * 
 */
public class LocalhostSmokeTestCase extends WebTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getTester().setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
	}

	protected void setAuthorization(String username, String password) { 
		final DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
		credentialsProvider.addCredentials(username, password);
		getTestContext().setAuthorization(username, password);
		getWebClient().setCredentialsProvider(credentialsProvider);
	}
	
	WebClient getWebClient() { 
		return ((HtmlUnitTestingEngineImpl)getTestingEngine()).
		  getWebClient();
	}
	public void beginAt(String url) {
		super.beginAt(url(url));
	}
	public void gotoPage(String url) {
		super.gotoPage(url(url));
	}
	
	public HtmlPage getPage(String url) throws Exception { 
		return (HtmlPage)getWebClient().getPage(url(url));
	}
	public XmlPage getXmlPage(String url) throws Exception { 
		return (XmlPage)getWebClient().getPage(url(url));
	}

	
    protected String url(String url) {
		return "http://localhost:8082" + url;
	}

    public void testAuthorisation() throws Exception {
		String collectionUrl =  "/chassis-wwarn-ui/atom/edit/studies";
		try {
			beginAt(collectionUrl);
		} catch (TestingEngineResponseException e) {
			if (e.getHttpStatusCode() == 401)
				setAuthorization("alice@example.org", "bar");
			else
				fail("Unexpected status " + e.getHttpStatusCode());
		}
		beginAt(collectionUrl);
		XmlPage feed = getXmlPage(collectionUrl);
		assertNull(feed.getNamespaceURI());
		assertNull(feed.getLocalName());
		assertNull(feed.getNodeValue());
		assertEquals("#document", feed.getNodeName());
		assertEquals("atom:feed", feed.getFirstChild().getNodeName());
	}

    /*
	public void testUiRunning() throws Exception {
		try {
			beginAt("/chassis-wwarn-ui/administrator");
		} catch (TestingEngineResponseException e) {
			assertEquals(401, e.getHttpStatusCode());
		}
		final DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
		credentialsProvider.addCredentials("alice@example.org", "bar");

		WebClient webClient = new WebClient();
		webClient.setCredentialsProvider(credentialsProvider);
		final HtmlPage administratorPage = webClient.getPage(url("/chassis-wwarn-ui/administrator"));
		assertEquals("WWARN - Administrator // alice@example.org", administratorPage.getTitleText());
		System.err.println(administratorPage.asXml());
		
		final HtmlPage startPage = webClient.getPage(url("/chassis-wwarn-ui/submitter/index.jsp"));
		assertEquals("WWARN - Submitter // alice@example.org", startPage.getTitleText());
		System.err.println(startPage.asXml());
		setAuthorization("alice@example.org","bar");
		gotoPage("/chassis-wwarn-ui/submitter/index.jsp");
		
	}
	
	
	public void testGenericRunning() throws Exception {
		try {
			beginAt("/chassis-generic-client-gwt/");
		} catch (TestingEngineResponseException e) {
			assertEquals(401, e.getHttpStatusCode());
		}

		setAuthorization("alice@example.org","bar");
		assertEquals("Chassis/*", 
				getPage("/chassis-generic-client-gwt/").getTitleText());
	}

	public void testWarnRunning() throws Exception {
		try {
			beginAt("/chassis-wwarn-client-gwt/");
		} catch (TestingEngineResponseException e) {
			assertEquals(401, e.getHttpStatusCode());
			setAuthorization("alice@example.org","bar");
			assertEquals("Chassis/WWARN", getPage("/chassis-wwarn-client-gwt/").getTitleText());
		}
	}
*/
    
	
}
