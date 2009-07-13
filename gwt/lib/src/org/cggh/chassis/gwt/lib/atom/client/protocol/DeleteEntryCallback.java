/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public interface DeleteEntryCallback {

	/**
	 * TODO document me
	 * 
	 * @param request
	 * @param exception
	 */
	public void onError(Request request, Throwable exception);

	/**
	 * TODO document me
	 * 
	 * @param request
	 * @param response
	 */
	public void onSuccess(Request request, Response response);

	/**
	 * TODO document me
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 */
	public void onError(Request request, Response response, Throwable exception);

	/**
	 * TODO document me
	 * 
	 * @param request
	 * @param response
	 */
	public void onFailure(Request request, Response response);

}
