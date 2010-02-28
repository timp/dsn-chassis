/**
 * 
 */
package org.cggh.chassis.generic.http.test;


import org.cggh.chassis.generic.http.AtomAuthorFilter;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2010/02/28
 */
public class AtomAuthorFilterTest extends TestCase {
	private final static String ATOM_ENTRY = 	
		"<?xml version='1.0' encoding='UTF-8'?>" + 
		"<atom:entry xmlns:atom=\"http://www.w3.org/2005/Atom\">" + 
		"<atom:title>An Atomic Entry</atom:title>" + 
        "<atom:author>" + 
        "<atom:email>notalice@example.org</atom:email>" +
        "</atom:author>" + 
		"</atom:entry>" + 
		"\n";
	
	private final static String ALICE = "notalice@example.org";
	private final static String PASSWORD = "bar";

	/**
	 * @param name
	 */
	public AtomAuthorFilterTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.cggh.chassis.generic.http.AtomAuthorFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}.
	 */
	public void testDoHttpFilter() throws Exception {
		AtomAuthorFilter it = new AtomAuthorFilter();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		it.doHttpFilter(request, response, chain);
		assertEquals(response.getStatus(), 200);
		
		response = new MockHttpServletResponse();
        request.setMethod("GET");		
		request.setContentType("application/atom+xml");
		it.doHttpFilter(request, response, chain);
		assertEquals(response.getStatus(), 200);

		response = new MockHttpServletResponse();
        request.setMethod("GET");
		request.setContentType("application/atom+xml");
		request.setContent("Not ATOM content");
		it.doHttpFilter(request, response, chain);
		assertEquals(response.getStatus(), 200);
		
		response = new MockHttpServletResponse();
        request.setMethod("GET");
		request.setContentType("application/atom+xml");
		request.setContent(ATOM_ENTRY);
		it.doHttpFilter(request, response, chain);
		assertEquals(response.getStatus(), 200);
		
		
		
	}

}
