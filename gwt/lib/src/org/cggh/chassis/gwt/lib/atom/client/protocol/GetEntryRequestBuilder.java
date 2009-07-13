/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class GetEntryRequestBuilder {

	private String encodedEntryURL = null;
	private AtomFactory factory = null;
	private GetEntryCallback callback = null;


	
	public GetEntryRequestBuilder(String encodedEntryURL) {
		this.encodedEntryURL = encodedEntryURL;
		this.factory = new AtomFactory();
	}
	
	
	
	public GetEntryRequestBuilder(String encodedEntryURL, AtomFactory factory) {
		this.encodedEntryURL = encodedEntryURL;
		this.factory = factory;
	}

	
	
	/**
	 * TODO document me
	 * 
	 * @return
	 */
	public RequestBuilder buildHTTPRequest() {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, this.encodedEntryURL);		
		RequestCallback glue = new GetEntryResponseHandler(this.callback, this.factory);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		
		return builder;

	}

	
	
	/**
	 * TODO document me
	 * @return 
	 * 
	 */
	public Request send() throws RequestException {

		// check precondition
		if (this.callback == null) {
			throw new RequestException("callback must be set");
		}
		
		// build request
		RequestBuilder builder = buildHTTPRequest();
		
		// send request
		return builder.send();

	}

}
