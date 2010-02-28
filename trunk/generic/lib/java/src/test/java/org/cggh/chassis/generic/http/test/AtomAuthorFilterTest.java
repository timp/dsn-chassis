/**
 * 
 */
package org.cggh.chassis.generic.http.test;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.cggh.chassis.generic.http.AtomAuthorFilter;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2010/02/28
 */
public class AtomAuthorFilterTest extends TestCase {
	private final static String ALICE = "notalice@example.org";
	private final static String PASSWORD = "bar";

	/**
	 * @param name
	 */
	public AtomAuthorFilterTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.http.AtomAuthorFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}.
	 */
	public void testDoHttpFilter_GET() throws Exception {
		AtomAuthorFilter it = new AtomAuthorFilter();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		it.doHttpFilter(request, response, chain);
		assertEquals(404,response.getStatus());
		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Nothing, OK");
        request.setMethod("GET");		
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());

		response = new MockHttpServletResponse();
		chain.setReturnFlag("Not atom, OK");
        request.setMethod("GET");
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());
		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod("GET");
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(401, response.getStatus());
		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod("GET");
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("Bob@example.org" + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(401, response.getStatus());

		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod("GET");
		request.setContentType("application/atom+xml");
		encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
		
		
		
	}
	/**
	 * Test method for {@link org.cggh.chassis.generic.http.AtomAuthorFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}.
	 */
	public void testDoHttpFilter_PUT() throws Exception {
		AtomAuthorFilter it = new AtomAuthorFilter();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		it.doHttpFilter(request, response, chain);
		assertEquals(404,response.getStatus());
		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Nothing, OK");
        request.setMethod("PUT");		
		request.setContentType("application/atom+xml");
		request.setRequestURI("atom/edit");
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());

		response = new MockHttpServletResponse();
		chain.setReturnFlag("Not atom, OK");
        request.setMethod("PUT");		
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());
		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod("PUT");		
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod("PUT");		
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("Bob@example.org" + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());

		
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod("PUT");		
		request.setContentType("application/atom+xml");
		encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
		
		
		
	}

}
