package org.cggh.chassis.generic.http.test;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class AtomAuthorFilterHeadTest extends AtomAuthorFilterTest {

	public AtomAuthorFilterHeadTest(String name) {
		super(name);
	}

	protected String getMethod() { 
		return "HEAD";
	}
	public void testDoHttpFilter_atomFound_atomContentType() throws Exception {
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
	}

	public void testDoHttpFilter_atomFound_atomContentType_bob() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("Bob@example.org" + ":" + "bar").getBytes())).replaceAll("\n", "");
		// System.err.println(":"+ encodedAuthorisationValue + ":");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
	}

	public void testDoHttpFilter_atomFound_atomContentType_lice() throws Exception {
		response = new MockHttpServletResponse();
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
		String encodedAuthorisationValue = 
			StringUtils.newStringUtf8(
					new Base64().encode(("lice@example.org:" + "bar").getBytes())).replaceAll("\n", "");
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
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
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());		
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
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(200, response.getStatus());
	}

	
}
