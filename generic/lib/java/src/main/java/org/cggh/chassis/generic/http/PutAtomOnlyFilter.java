package org.cggh.chassis.generic.http;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Disallow update of non-atom resources. 
 *
 * @author timp
 * @since  2010/02/22
 *
 */
public class PutAtomOnlyFilter extends HttpFilter {

	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("PUT") && !request.getContentType().startsWith("application/atom+xml"))
          response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only Atom entries may be updated");
        else 
          chain.doFilter(request, response);
     }
}

