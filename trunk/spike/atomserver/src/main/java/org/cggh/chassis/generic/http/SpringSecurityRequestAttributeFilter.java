/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exist.xquery.value.ValueSequence;
import org.exist.xquery.value.StringValue;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;

/**
 * @author aliman
 *
 */
public class SpringSecurityRequestAttributeFilter extends HttpFilter {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("request inbound");

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		log.debug("found username: "+name);
		
		request.setAttribute("user-name", new StringValue(name));
		
		GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

		ValueSequence roles = new ValueSequence();
		
		for (GrantedAuthority a : authorities) {
			log.debug("found role: "+a.toString());
			StringValue s = new StringValue(a.toString());
			roles.add(s);
		}

		request.setAttribute("user-roles", roles);
		
		chain.doFilter(request, response);

		log.debug("response outbound");
	}

}
