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
 * @author aliman
 *
 */
public class BufferedHttpFilter extends HttpFilter {

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.http.HttpFilter#doHttpFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doHttpFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

        BufferedHttpResponseWrapper responseWrapper = new BufferedHttpResponseWrapper((HttpServletResponse) res);
        chain.doFilter(req, responseWrapper);
        
        byte[] data = responseWrapper.getBuffer();
        res.setContentLength(data.length);
        res.getOutputStream().write(data);
        res.flushBuffer();
		
	}

}
