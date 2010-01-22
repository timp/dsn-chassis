/**
 * 
 */
package org.cggh.chassis.generic.async.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;

/**
 * @author aliman
 *
 */
public class HttpHelper {

	
	
	/**
	 * @param requestBuilder
	 * @param deferredResult
	 */
	@SuppressWarnings("unchecked")
	public static void sendRequest(RequestBuilder requestBuilder, HttpDeferred deferredResult) {

		try {

			Request request = requestBuilder.send();
			deferredResult.setCanceller(new HttpCanceller(request));
			
		}
		catch (Throwable t) {

			deferredResult.errback(t);

		}
				
	}
	
	

	
}
