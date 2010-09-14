/**
 * 
 */
package org.cggh.chassis.generic.miniatom.client;

import org.cggh.chassis.generic.async.client.HttpCallbackBase;
import org.cggh.chassis.generic.async.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class CallbackWithDocument
	extends HttpCallbackBase 
	implements RequestCallback {

	private HttpDeferred<Document> result;

	/**
	 * @param factory
	 * @param result 
	 */
	public CallbackWithDocument(HttpDeferred<Document> result) {
		super(result);
		this.result = result;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onResponseReceived(com.google.gwt.http.client.Request, com.google.gwt.http.client.Response)
	 */
	public void onResponseReceived(Request request, Response response) {

		super.onResponseReceived(request, response);
		
		try {
			
			// check preconditions
			checkResponsePreconditions(request, response);

			// parse the response
			Document d = XMLParser.parse(this.responseText);
			
			// pass through result
			result.callback(d);
			
		} catch (Throwable t) {

			// pass through as error
			result.errback(t);

		}

	}

}
