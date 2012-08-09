package org.cggh.chassis.manta.http.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import junit.framework.TestCase;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpMethod;
/**
 * Not so much a test as documentation.
 */
public class DOILookupTest extends TestCase {

	public static final HttpClient client = new DefaultHttpClient();

	private String getResponseBody(HttpResponse httpResponse) throws IOException {
		InputStream is = httpResponse.getEntity().getContent(); 
		Writer writer = new StringWriter();

		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		return writer.toString();
	}
	public void testGoodGet() throws Exception {
		String doi ="10.1371/journal.pgen.1000508";
		String url = "http://dx.doi.org/" + doi;
		
		HttpGet method = new HttpGet(url);
		HttpResponse httpResponse = client.execute(method);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		assertEquals(200,statusCode);
		assertFalse(getResponseBody(httpResponse).contains("Not Found"));
	}

	public void testBadNamingAuthorityGet() throws Exception {
		
		String url = "http://dx.doi.org/bad";
		HttpGet method = new HttpGet(url);
		HttpResponse httpResponse = client.execute(method);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		String response = getResponseBody(httpResponse);
		assertTrue(response.contains("DOI Naming Authority"));
		assertTrue(response.contains("Not Found"));
		assertEquals(404,statusCode);
	}
	public void testGoodNamingAuthorityBadDOIGet() throws Exception {
		
		String url = "http://dx.doi.org/10.1186/1471-2105-8-487bad";
		HttpGet method = new HttpGet(url);
		HttpResponse httpResponse = client.execute(method);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		
		String response = getResponseBody(httpResponse);
		assertTrue(response.contains("Not Found"));
		assertEquals(404,statusCode);	
	}
	
	
}
