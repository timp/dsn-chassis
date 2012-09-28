package org.cggh.chassis.manta.http.servlet;

import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.MalformedURLException;

/**
 * Not so much a test as documentation.
 */
public class StoreUploadsServletTest extends TestCase {

	    public void testURL() throws MalformedURLException {
            String testURL = "https://demo.wwarn.org:443/repo/service/content/media/curated/MNZMZ";
            MockHttpServletRequest mockReq = new MockHttpServletRequest();

            StoreUploadsServlet.setRequestURL(testURL, mockReq);
            String uri = StoreUploadsServlet.requestToURL(mockReq);

            assertEquals(testURL,uri);

        }
	
	
}
