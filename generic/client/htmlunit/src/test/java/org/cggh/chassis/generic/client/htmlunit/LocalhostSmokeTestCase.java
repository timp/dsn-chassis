package org.cggh.chassis.generic.client.htmlunit;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import net.sourceforge.jwebunit.junit.WebTestCase;
import net.sourceforge.jwebunit.exception.TestingEngineResponseException;

public class LocalhostSmokeTestCase extends WebTestCase {

	public void testTomcatRunning() throws Exception {
		beginAt("/");
		assertTitleEquals("Apache Tomcat");
	}

	public void testChassisRunning() throws Exception {
		try {
			beginAt("/chassis-generic-client-gwt/");
		} catch (TestingEngineResponseException e) {
			assertEquals(401, e.getHttpStatusCode());
		}
		final DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
		credentialsProvider.addCredentials("alice@example.org", "bar");

		WebClient webClient = new WebClient();
		webClient.setCredentialsProvider(credentialsProvider);
		final HtmlPage startPage = webClient
				.getPage(url("/chassis-generic-client-gwt/"));
		assertEquals("Chassis/*", startPage.getTitleText());
	}

	public void beginAt(String url) {
		super.beginAt(url(url));
	}

	private String url(String url) {

		return "http://localhost:8080" + url;
	}

}
