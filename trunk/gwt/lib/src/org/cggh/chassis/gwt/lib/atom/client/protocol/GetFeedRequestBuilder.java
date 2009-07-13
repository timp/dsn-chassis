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
public class GetFeedRequestBuilder {

	protected AtomFactory factory = null;
	protected String encodedCollectionURL = null;
	protected GetFeedCallback callback = null;

	public GetFeedRequestBuilder(String encodedCollectionURL) {
		this.encodedCollectionURL = encodedCollectionURL;
		this.factory = new AtomFactory();
	}
	
	public GetFeedRequestBuilder(String collectionURL, AtomFactory factory) {
		this.encodedCollectionURL = collectionURL;
		this.factory = factory;
	}
	
	public void setCallback(GetFeedCallback callback) {
		this.callback  = callback;
	}
	
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
	
	protected RequestBuilder buildHTTPRequest() {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, this.encodedCollectionURL);		
		RequestCallback glue = new GetFeedResponseHandler(this.callback, this.factory);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		
		return builder;
		
	}
	
}
