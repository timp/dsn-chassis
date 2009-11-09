/**
 * 
 */
package org.cggh.chassis.generic.async.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class HttpCallbackBase implements RequestCallback {

	protected Set<Integer> expectedStatusCodes = new HashSet<Integer>();
	private Log log = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("unchecked")
	protected HttpDeferred genericResult;
	
	@SuppressWarnings("unchecked")
	protected HttpCallbackBase(HttpDeferred genericResult) {
		this.genericResult = genericResult;
	}
	
	public void onResponseReceived(Request request, Response response) {
		log.enter("onResponseReceived");
		try {

			// log response details
			log.debug("response status: "+response.getStatusCode() + " " + response.getStatusText());
			log.debug("response headers: "+response.getHeadersAsString());
//			log.debug("response body: "+response.getText());
//			log.debug("content length (actual): "+response.getText().length());
			
			// store request and response
			genericResult.addRequest(request);
			genericResult.addResponse(response);
			
		} catch (Throwable t) {

			// pass through as error
			genericResult.errback(t);

		}

		log.leave();
	}

	
	/* (non-Javadoc)
	 * @see com.google.gwt.http.client.RequestCallback#onError(com.google.gwt.http.client.Request, java.lang.Throwable)
	 */
	public void onError(Request request, Throwable exception) {
		this.genericResult.errback(exception);
	}

	protected void checkResponsePreconditions(Request request, Response response) throws HttpException {

		// precondition: check status code
		checkStatusCode(request, response);
		
		// precondition: check content type
		checkContentType(request, response);
		
	}
	
	protected void checkStatusCode(Request request, Response response) throws HttpException {

		// precondition: check status code
		int statusCode = response.getStatusCode();
		if ( ! this.expectedStatusCodes.contains(statusCode) ) {
			throw new HttpException("bad status code, expected one of "+formatExpectedStatusCodes()+", found "+statusCode, request, response);
		}
		
	}
	
	protected void checkContentType(Request request, Response response) throws HttpException {

		// precondition: check content type
		String contentType = response.getHeader("Content-Type");
		if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
			throw new HttpException("bad content type, expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\"", request, response);
		}

	}
	
	protected String formatExpectedStatusCodes() {
		String output = "{";
		for (Iterator<Integer> it = expectedStatusCodes.iterator(); it.hasNext(); ) {
			output += it.next().toString();
			if (it.hasNext()) {
				output += ", ";
			}
		}
		output += "}";
		return output;
	}

}
