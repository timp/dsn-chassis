/**
 * 
 */
package org.cggh.chassis.generic.http;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author aliman
 *
 */
public class RequestHeaderOverrideRequestWrapper extends HttpServletRequestWrapper {

	private Map<String, String> override;
	private Vector<String> headerNames;
	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * @param request
	 */
	public RequestHeaderOverrideRequestWrapper(HttpServletRequest request, Map<String,String> override) {
		super(request);
		this.override = override;
	}
	
	@Override
	public String getHeader(String header) {
		log.debug("getHeader("+header+") called");
		String normalisedHeader = header.toLowerCase();
		if (override.containsKey(normalisedHeader)) {
			String value = override.get(normalisedHeader);
			log.debug("overriding header with value: "+value);
			return value;
		}
		else {
			log.debug("not overriding header");
			return ((HttpServletRequest)this.getRequest()).getHeader(header);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getHeaderNames() {
		log.debug("getHeaderNames() called");
		if (headerNames == null) {
			headerNames = new Vector<String>();
			Enumeration wrappedHeaderNames = ((HttpServletRequest)this.getRequest()).getHeaderNames();
			for (;wrappedHeaderNames.hasMoreElements();) {
				String headerName = (String) wrappedHeaderNames.nextElement();
				String normalisedHeaderName = headerName.toLowerCase();
				if (override.get(normalisedHeaderName) != null) {
					headerNames.add(headerName);
				}
			}
		}
		log.debug("header names: "+headerNames.toArray());
		return headerNames.elements();
	}
	
	@Override 
	public String getContentType() {
		return getHeader("Content-Type");
	}
		
}
