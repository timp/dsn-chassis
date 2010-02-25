package org.cggh.chassis.spike.atomserver;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import junit.framework.TestCase;

public class TestAtomServer extends TestCase {
	
	
	
	public void testCreateCollection() {
		
		try {
			
			PostMethod method = new PostMethod("http://localhost:8081/atomserver/atomserver/foo");

			RequestEntity entity = new StringRequestEntity(
					"<atom:feed xmlns:atom=\"http://www.w3.org/2005/Atom\"><atom:title>Test Collection</atom:title></atom:feed>",
					"application/atom+xml;type=feed",
					"UTF-8");
			
			method.setRequestEntity(entity);
			
			HttpClient client = new HttpClient();
			
			int result = client.executeMethod(method);
			
			assertEquals(201, result);

		}
		catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
		
	}

}
