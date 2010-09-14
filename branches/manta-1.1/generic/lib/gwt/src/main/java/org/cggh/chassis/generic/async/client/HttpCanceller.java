/**
 * 
 */
package org.cggh.chassis.generic.async.client;


import com.google.gwt.http.client.Request;

/**
 * @author aliman
 *
 */
@SuppressWarnings("unchecked")
public class HttpCanceller implements Function {

	private Request request;

	/**
	 * @param request
	 */
	public HttpCanceller(Request request) {
		this.request = request;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.twisted.client.Function#apply(java.lang.Object)
	 */
	public Object apply(Object in) {
		this.request.cancel();
		return null;
	}

}
