/**
 * 
 */
package org.cggh.chassis.generic.http.test;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.cggh.chassis.generic.http.AtomAuthorFilter;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2010/02/28
 */
public class AtomAuthorFilterTest extends TestCase {
	AtomAuthorFilter it = null;
	MockHttpServletRequest request = null;
	MockHttpServletResponse response = null;
	MockFilterChain chain = null;

	
	protected final static String ALICE = "alice@example.org";
	protected final static String PASSWORD = "bar";

	/**
	 * @param name
	 */
	public AtomAuthorFilterTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		it = new AtomAuthorFilter();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		chain = new MockFilterChain();
		request.setRequestURI("/atom/edit/studies");
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("id", new String[]{"jhgjhgjh"});
		request.setParameters(params);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected String getMethod() { 
		return "GET";
	}
	/**
	 * Test method for {@link org.cggh.chassis.generic.http.AtomAuthorFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}.
	 */
	public void testDoHttpFilter_notFound() throws Exception {
        request.setMethod(getMethod());
		request.setParameters(null);
		chain.setReturnFlag("Nothing, 404");
		it.doHttpFilter(request, response, chain);
		assertEquals(404,response.getStatus());
	}
	public void testDoHttpFilter_emptyFound_nullContentType() throws Exception {
		chain.setReturnFlag("Nothing, OK");
        request.setMethod(getMethod());		
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());
	}
	public void testDoHttpFilter_emptyFound_unknownContentType() throws Exception {
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("id", new String[]{"jhgjhgjh"});
		params.put("multi", new String[]{"one","two"});
		request.setParameters(params);
		chain.setReturnFlag("Nothing, OK");
        request.setMethod(getMethod());		
		request.setContentType("unknown");
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());
	}
	public void testDoHttpFilter_nonAtomFound_atomContentType() throws Exception {
		chain.setReturnFlag("Not atom, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		assertEquals("http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d", request.getRequestURL().toString());
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType() throws Exception {
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		request.setContextPath("/maps");
		//http://maps.google.com/maps/feeds/maps/104684776855932063951/full
		//request.setRequestURI("/feeds/maps/userID/full");

		// 403
		//http://maps.google.com/maps/feeds/maps/216878860856967875807/full		
		request.setRequestURI("/feeds/maps/216878860856967875807/full");
		request.resetParameters();
		request.setServerName("maps.google.com");
		assertEquals("http://maps.google.com/maps/feeds/maps/216878860856967875807/full", request.getRequestURL().toString());

		it.doHttpFilter(request, response, chain);
		assertEquals(401, response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType_bob() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("Bob@example.org" + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		assertEquals("http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d", request.getRequestURL().toString());
		it.doHttpFilter(request, response, chain);
		assertEquals(401, response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType_alice() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType_alice_BrokenAuth() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Broken "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (IllegalArgumentException e) { 
			assertTrue(e.getMessage().startsWith("Authorization not 'Basic '"));
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomMalformed() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Malformed, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertEquals("Malformed XML",e.getMessage());
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomNoAuthor() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("No author, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertTrue(e.getMessage().startsWith("No author element"));
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomNoEmail() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("No email, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertTrue(e.getMessage().startsWith("No email elements"));
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomTwoAuthors() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Two authors, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertTrue(e.getMessage().startsWith("More than one author element"));
		}
	}

    /**
     *  Prove that lice@example.org cannot access items owned by alice@example.org.
     *  This is part of the contract of List.contains, but just to prove it.  
     * 
     */
	public void testDoHttpFilter_atomFound_atomContentType_lice() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("lice@example.org:" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		// 200
		//http://maps.google.com/maps/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/216878860856967875806/full/00047d1c763a30ff4db1d");
		request.resetParameters();
		request.setServerName("maps.google.com");
		it.doHttpFilter(request, response, chain);
		assertEquals(401, response.getStatus());
	}

	public void testDoHttpFilter_serverError() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, 500");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("alice@example.org:" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		//500
		//http://maps.google.com/maps/feeds/maps/default/full
		request.setContextPath("/maps");
		request.setRequestURI("/feeds/maps/default/full");
		request.resetParameters();
		request.setServerName("maps.google.com");
		assertEquals("http://maps.google.com/maps/feeds/maps/default/full", request.getRequestURL().toString());

		it.doHttpFilter(request, response, chain);
		assertEquals(500, response.getStatus());
	}


}
