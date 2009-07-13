/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.protocol;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
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
public class PutEntryRequestBuilder {

	private String encodedEntryUrl = null;
	private AtomEntry entry = null;
	private PutEntryCallback callback = null;
	private AtomFactory factory = null;

	
	/**
	 * @param encode
	 */
	public PutEntryRequestBuilder(String encodedEntryUrl) {
		this.encodedEntryUrl = encodedEntryUrl;
		this.factory = new AtomFactory();
	}

	
	/**
	 * @param encode
	 */
	public PutEntryRequestBuilder(String encodedEntryUrl, AtomFactory factory) {
		this.encodedEntryUrl = encodedEntryUrl;
		this.factory = factory;
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

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, this.encodedEntryUrl);		
		RequestCallback glue = new PutEntryResponseHandler(this.callback, this.factory);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = this.entry.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setHeader("X-HTTP-Method-Override", "PUT");
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
	 * @param putEntryCallback
	 */
	public void setCallback(PutEntryCallback callback) {
		this.callback  = callback;
	}

}
