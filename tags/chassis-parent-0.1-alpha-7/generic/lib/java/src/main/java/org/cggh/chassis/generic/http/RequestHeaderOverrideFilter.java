/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author aliman
 *
 */
public class RequestHeaderOverrideFilter extends HttpFilter {

	private static final String INITPARAM_HEADERNAME = "headerName";
	private static final String INITPARAM_HEADERVALUE = "headerValue";
	private Log log = LogFactory.getLog(this.getClass());
	
	private HashMap<String, String> override;

	@Override
	public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug("request inbound");
		chain.doFilter(new RequestHeaderOverrideRequestWrapper(request, override), response);
		log.debug("response outbound");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		override = new HashMap<String,String>();
		
		String headerName = filterConfig.getInitParameter(INITPARAM_HEADERNAME);
		if (headerName == null) {
			throw new ServletException("init param "+INITPARAM_HEADERNAME+" must be provided");
		}
		
		String headerValue = filterConfig.getInitParameter(INITPARAM_HEADERVALUE);
		log.debug("found override headerName: "+headerName+"; headerValue: "+headerValue);
		override.put(headerName.toLowerCase(), headerValue);
		
	}

}
