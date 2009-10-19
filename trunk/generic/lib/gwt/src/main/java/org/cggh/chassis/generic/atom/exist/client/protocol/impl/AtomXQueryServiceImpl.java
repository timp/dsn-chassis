/**
 * 
 */
package org.cggh.chassis.generic.atom.exist.client.protocol.impl;

import org.cggh.chassis.generic.atom.exist.client.protocol.AtomXQueryService;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.HttpCanceller;
import org.cggh.chassis.generic.twisted.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;

/**
 * @author aliman
 *
 */
public class AtomXQueryServiceImpl implements AtomXQueryService {

	
	
	
	private String serviceUrl;
	
	
	
	
	public AtomXQueryServiceImpl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomXQueryService#query(java.lang.String)
	 */
	public Deferred<String> query(String xquery) {
		
		HttpDeferred<String> deferredResult = new HttpDeferred<String>();
		
		RequestBuilder requestBuilder = buildXQueryRequest(serviceUrl, xquery);

		requestBuilder.setCallback(new XQueryRequestCallback(deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}




	/**
	 * @param serviceUrl2
	 * @param xquery
	 * @return
	 */
	public static RequestBuilder buildXQueryRequest(String serviceUrl, String xquery) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(serviceUrl));		
		builder.setHeader("Accept", "*/*");
		builder.setHeader("Content-Type", "application/xquery;charset=\"utf-8\"");
		builder.setHeader("Content-Length", Integer.toString(xquery.length()));
		// TODO other headers?
		
		builder.setRequestData(xquery);

		return builder;

	}
	

	// TODO refactor, code smell
	
	/**
	 * @param requestBuilder
	 * @param deferredResult
	 */
	@SuppressWarnings("unchecked")
	private static void sendRequest(RequestBuilder requestBuilder, Deferred deferredResult) {

		try {

			Request request = requestBuilder.send();
			deferredResult.setCanceller(new HttpCanceller(request));
			
		}
		catch (Throwable t) {

			deferredResult.errback(t);

		}
				
	}
	

	
}
