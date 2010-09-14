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

	static Thread serverThread = null;
	
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
		if (serverThread == null) { 
			serverThread = new Thread(new TestServer());
			serverThread.start();
		}
		it = new AtomAuthorFilter();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		chain = new MockFilterChain();
		request.setContextPath("/org/cggh/chassis/generic/http/test");
		request.setRequestURI("/entry.atom");
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("id", new String[]{"study1"});
		request.setParameters(params);
		request.setPort(8089);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected String getMethod() { 
		return "GET";
	}
	private int unlessDelete(int i) {
		if (getMethod().equals("DELETE"))
			return 403;
		return i;
	}
	protected int unlessPost(int i) {
		if (getMethod().equals("POST"))
			return 400;
		return i;
	}
	
	
	public void testDoHttpFilter_notFound() throws Exception {
        request.setMethod(getMethod());
		request.resetParameters();
		chain.setReturnFlag("Nothing, 404");
		request.setRequestURI("/notfound.atom");
		it.doHttpFilter(request, response, chain);
		assertEquals(404,response.getStatus());
	}
	public void testDoHttpFilter_notFound_atomContentType() throws Exception {
        request.setMethod(getMethod());
		request.resetParameters();
		chain.setReturnFlag("Nothing, 404");
		request.setRequestURI("/notfound.atom");
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessDelete(404),response.getStatus());
	}
	public void testDoHttpFilter_emptyFound_nullContentType() throws Exception {
		chain.setReturnFlag("Nothing, 200");
        request.setMethod(getMethod());
        request.setContentType(null);
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());
	}
	public void testDoHttpFilter_emptyFound_unknownContentType() throws Exception {
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("id", new String[]{"jhgjhgjh"});
		params.put("multi", new String[]{"one","two"});
		request.setParameters(params);
		chain.setReturnFlag("Nothing, 200");
        request.setMethod(getMethod());		
		request.setContentType("unknown");
		it.doHttpFilter(request, response, chain);
		assertEquals(200,response.getStatus());
	}
	public void testDoHttpFilter_nonAtomFound_atomContentType() throws Exception {
		chain.setReturnFlag("Not atom, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		request.setRequestURI("/notAtom.txt");
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(unlessDelete(200)),response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType() throws Exception {
		chain.setReturnFlag("Atom Entry, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessDelete(unlessPost(401)), response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType_bob() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, 200");		
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("Bob@example.org" + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(403), response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType_alice() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(unlessDelete(200)), response.getStatus());
	}
	public void testDoHttpFilter_atomFound_atomContentType_alice_HEAD() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, 200");
        request.setMethod("HEAD");
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertTrue(e.getMessage().startsWith("Unexpected value of request method"));
		}
	}
	public void testDoHttpFilter_atomFound_atomContentType_alice_BrokenAuth() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Broken "
				+ encodedAuthorisationValue);
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (IllegalArgumentException e) { 
			assertTrue(e.getMessage().startsWith("Authorization not 'Basic '"));
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomMalformed() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Malformed, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		request.setRequestURI("/malformed.atom");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertEquals("Malformed XML",e.getMessage());
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomNoAuthor() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("No author, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		request.setRequestURI("/noAuthor.atom");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertTrue(e.getMessage().startsWith("No author element"));
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomNoEmail() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("No email, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		request.setRequestURI("/noEmail.atom");
		try { 
			it.doHttpFilter(request, response, chain);
			fail("Should have bombed");
		} catch (RuntimeException e) { 
			assertTrue(e.getMessage().startsWith("No email elements"));
		}
	}

	public void testDoHttpFilter_atomFound_atomContentType_alice_badAtomTwoAuthors() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Two authors, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		request.setRequestURI("/twoAuthors.atom");
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
		chain.setReturnFlag("Atom Entry, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("lice@example.org:" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(403), response.getStatus());
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

		it.doHttpFilter(request, response, chain);
		assertEquals(unlessDelete(unlessPost(500)), response.getStatus());
	}

	public void testDoHttpFilter_atomFeedFound_atomContentType_alice() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Feed, 200");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode((ALICE + ":" + PASSWORD).getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		request.setRequestURI("/emptyStudiesFeed.atom");
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessDelete(200), response.getStatus());
	}

}
