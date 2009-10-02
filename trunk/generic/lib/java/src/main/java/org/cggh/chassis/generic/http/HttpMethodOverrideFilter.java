/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aliman
 *
 */
public class HttpMethodOverrideFilter extends HttpFilter {

	public static final String INITPARAM_ALLOWEDOVERRIDES = "allowedOverrides";
	private Set<String> allowedOverrides;

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.http.HttpFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new HttpMethodOverrideRequestWrapper(request, allowedOverrides), response);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		allowedOverrides = new HashSet<String>();
		
		String allowedOverridesValue = filterConfig.getInitParameter(INITPARAM_ALLOWEDOVERRIDES);
		
		if (allowedOverridesValue != null) {
			String[] tokens = allowedOverridesValue.split(" ");
			for (String token : tokens) {
				allowedOverrides.add(token.trim());
			}
		}
		else {
			// default
			allowedOverrides.add("PUT");
			allowedOverrides.add("DELETE");
		}
	}
	

}
