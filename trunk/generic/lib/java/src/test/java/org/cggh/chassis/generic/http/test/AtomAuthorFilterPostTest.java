package org.cggh.chassis.generic.http.test;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class AtomAuthorFilterPostTest extends AtomAuthorFilterTest {

	public AtomAuthorFilterPostTest(String name) {
		super(name);
	}

	protected String getMethod() { 
		return "POST";
	}
	public void testDoHttpFilter_atomFound_atomContentType() throws Exception {
		chain.setReturnFlag("Atom Entry, OK");
        request.setMethod(getMethod());
		request.setContentType("application/atom+xml");
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
		request.setHeader("Authorization", "Basic "
				+ encodedAuthorisationValue);
		it.doHttpFilter(request, response, chain);
		assertEquals(401, response.getStatus());
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
		it.doHttpFilter(request, response, chain);
		assertEquals(401, response.getStatus());
	}
}
