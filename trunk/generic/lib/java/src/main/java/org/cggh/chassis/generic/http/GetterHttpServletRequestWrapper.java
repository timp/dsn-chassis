package org.cggh.chassis.generic.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Enable the conversion of a write method to a GET. 
 * 
 * @author timp
 * @since 2010/02/27
 */
public class GetterHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private String originalMethod;
	public GetterHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		originalMethod = request.getMethod();
	}
    String getOriginalMethod() { 
    	return originalMethod;
    }
    @Override
    public String getMethod() { 
    	return "GET";
    }
}
