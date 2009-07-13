/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;

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
public class PostEntryRequestBuilder {

	private String encodedCollectionUrl = null;
	private AtomEntry entry = null;
	private PostEntryCallback callback = null;

	/**
	 * @param encode
	 */
	public PostEntryRequestBuilder(String encodedCollectionUrl) {
		this.encodedCollectionUrl = encodedCollectionUrl;
	}

	/**
	 * TODO document me
	 * 
	 * @param entry
	 */
	public void setEntry(AtomEntry entry) {
		this.entry  = entry;
	}

	/**
	 * TODO document me
	 * 
	 * @return
	 */
	public RequestBuilder buildHTTPRequest() {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, this.encodedCollectionUrl);		
		RequestCallback glue = new PostEntryResponseHandler(this.callback);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = this.entry.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		// TODO other headers?
		
		builder.setRequestData(content);

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

		// check precondition
		if (this.entry == null) {
			throw new RequestException("entry must be set");
		}
		
		// build request
		RequestBuilder builder = buildHTTPRequest();
		
		// send request
		return builder.send();

	}

	/**
	 * TODO document me
	 * 
	 * @param postEntryCallback
	 */
	public void setCallback(PostEntryCallback callback) {
		this.callback  = callback;
	}

}
