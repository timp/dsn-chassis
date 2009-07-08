/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.http.client;

import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public interface ResponseWrapper {

	public int getStatusCode();
	public String getHeader(String header);
	public Response getWrappee();
	public String getText();
	
}
