/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.context.SecurityContextHolder;

/**
 * @author aliman
 *
 */
public class SpringSecuritySetUserNameAttributeFilter extends HttpFilter {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("request inbound");

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		log.debug("found username: "+name);
		
		request.setAttribute("username", name);
		chain.doFilter(request, response);

		log.debug("response outbound");
	}

}
