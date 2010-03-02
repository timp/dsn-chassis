package org.cggh.chassis.generic.http.test;

public class AtomAuthorFilterPutTest extends AtomAuthorFilterTest {

	public AtomAuthorFilterPutTest(String name) {
		super(name);
	}
	protected String getMethod() { 
		return "PUT";
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
		assertEquals(403, response.getStatus());
	}
}
