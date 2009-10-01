/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aliman
 *
 */
public class ContentTypeOverrideFilter extends HttpFilter {

	private static final String INITPARAM_CONTENTTYPE = "contentType";
	String contentType;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		contentType = filterConfig.getInitParameter(INITPARAM_CONTENTTYPE);
		if (contentType == null) {
			throw new ServletException("init param "+INITPARAM_CONTENTTYPE+" must be provided");
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.http.HttpFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doHttpFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) res);
        chain.doFilter(req, responseWrapper);
        
        byte[] data = responseWrapper.getBuffer();
        res.setContentType(contentType); 
        res.setContentLength(data.length);
        res.getOutputStream().write(data);
        res.flushBuffer();

	}

}
