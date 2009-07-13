/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;


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
public class DeleteEntryRequestBuilder {

	private String encodedEntryUrl = null;
	private DeleteEntryCallback callback = null;

	/**
	 * @param encode
	 */
	public DeleteEntryRequestBuilder(String encodedEntryUrl) {
		this.encodedEntryUrl = encodedEntryUrl;
	}

	/**
	 * TODO document me
	 * 
	 * @return
	 */
	public RequestBuilder buildHTTPRequest() {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, this.encodedEntryUrl);		
		RequestCallback glue = new DeleteEntryResponseHandler(this.callback);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("X-HTTP-Method-Override", "DELETE");
		// TODO other headers?

		return builder;

	}

	/**
	 * TODO document me
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
	
	
	public void setCallback(DeleteEntryCallback callback) {
		this.callback = callback;
	}


}
