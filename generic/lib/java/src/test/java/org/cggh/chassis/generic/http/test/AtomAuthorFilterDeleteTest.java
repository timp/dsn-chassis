package org.cggh.chassis.generic.http.test;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class AtomAuthorFilterDeleteTest extends AtomAuthorFilterTest {

	public AtomAuthorFilterDeleteTest(String name) {
		super(name);
	}

	protected String getMethod() { 
		return "DELETE";
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
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(403), response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(403), response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(403), response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(403), response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(unlessPost(403), response.getStatus());
	}
	
	
}
