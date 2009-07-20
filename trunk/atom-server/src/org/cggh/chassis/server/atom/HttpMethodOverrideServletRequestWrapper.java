/**
 * $Id$
 */
package org.cggh.chassis.server.atom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class HttpMethodOverrideServletRequestWrapper extends HttpServletRequestWrapper {

	private static final String XHTTPMETHODOVERRIDE = "X-HTTP-Method-Override";

	/**
	 * @param request
	 */
	public HttpMethodOverrideServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	public String getMethod() {
		if (super.getMethod().equals("POST")) {
			String override = this.getHeader(XHTTPMETHODOVERRIDE);
			if (override != null && !override.equals("")) {
				return override;
			}
		}
		return super.getMethod();
	}

}
