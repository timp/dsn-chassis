/**
 * 
 */
package org.cggh.chassis.generic.async.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class HttpException extends Exception {

	private Request request;
	private Response response;

	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * @param response 
	 * @param request 
	 * @param string
	 */
	public HttpException(String message, Request request, Response response) {
		super(message);
		this.request = request;
		this.response = response;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
