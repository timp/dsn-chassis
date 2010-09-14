/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A filter which may inspect the response content. 
 * 
 * @author aliman
 *
 */
public class BufferedHttpFilter extends HttpFilter {
	
	protected byte[] content; 

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.http.HttpFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);
        
        content = responseWrapper.getBuffer();
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
        response.flushBuffer();
	} 
	
	protected byte [] getContent() { 
		return content;
	}

}
