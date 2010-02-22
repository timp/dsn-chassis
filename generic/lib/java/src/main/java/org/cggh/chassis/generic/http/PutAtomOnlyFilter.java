/**
 * 
 */
package org.cggh.chassis.generic.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request.getMethod().equals("PUT"))   // HttpServlet.METHOD_PUT is private !!
            if (!request.getContentType().equals("application/atom+xml"))
                response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Only Atom entries may be updated");

	}
}

