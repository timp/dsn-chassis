package org.cggh.chassis.wwarn.client.htmlunit;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.exception.TestingEngineResponseException;

/**
 * @author Tim.Pizey
 * @since 01 December 2009
 * 
 */
public class LocalhostSmokeTestCase extends WebTestCase {

	public void testTomcatRunning() throws Exception {
		beginAt("/");
		assertTitleEquals("Apache Tomcat");
	}

	public void testChassisRunning() throws Exception {
		try {
			beginAt("/chassis-wwarn-client-gwt/");
		} catch (TestingEngineResponseException e) {
			assertEquals(401, e.getHttpStatusCode());
		}
		final DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
		credentialsProvider.addCredentials("alice@example.org", "bar");

		WebClient webClient = new WebClient();
		webClient.setCredentialsProvider(credentialsProvider);
		final HtmlPage startPage = webClient
				.getPage(url("/chassis-wwarn-client-gwt/"));
		assertEquals("Chassis/WWARN", startPage.getTitleText());
	}

	public void beginAt(String url) {
		super.beginAt(url(url));
	}

	private String url(String url) {
		return "http://localhost:8080" + url;
	}

}
