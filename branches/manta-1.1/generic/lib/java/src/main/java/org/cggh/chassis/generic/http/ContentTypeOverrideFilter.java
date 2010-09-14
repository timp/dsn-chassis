/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Enable content type to be changed from "application/atom+xml" to "application/xml",
 * to work around a bug in GWT 1.7 hosted mode browser, where content was truncated
 * if contentType was "application/atom+xml". Not removed as "application/xml" is
 * rendered nicely by Firefox.
 *
 * @author aliman
 *
 */
public class ContentTypeOverrideFilter extends HttpFilter {

	private static final String INITPARAM_CONTENTTYPE = "contentType";
	String contentType;
	private Log log = LogFactory.getLog(this.getClass());
	
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
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// N.B. you have to buffer the response because you cannot set the
		// response content type once the response is committed.

        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper(response);
        chain.doFilter(request, responseWrapper);
        
        byte[] data = responseWrapper.getBuffer();

        if (response.getContentType() != null && response.getContentType().startsWith("application/atom+xml")) {
        	// only override application/atom+xml ... so we don't confuse media resource downloads
        	response.setContentType(contentType); 

            // TODO make this more generic, i.e., configurable
        }

        response.setContentLength(data.length);
        response.getOutputStream().write(data);
        response.flushBuffer();

	}

}
