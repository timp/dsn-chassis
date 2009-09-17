/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.twisted.client.Deferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;

/**
 * @author aliman
 *
 */
public class AtomServiceImpl implements AtomService {

	
	
	private AtomFactory factory;




	public AtomServiceImpl(AtomFactory factory) {
		this.factory = factory;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#deleteEntry(java.lang.String)
	 */
	public Deferred<Void> deleteEntry(String entryURL) {

		Deferred<Void> deferredResult = new Deferred<Void>();

		RequestBuilder requestBuilder = buildDeleteEntryRequest(entryURL);
		
		requestBuilder.setCallback(new DeleteEntryCallback(deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	public static RequestBuilder buildDeleteEntryRequest(String entryURL) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(entryURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("X-HTTP-Method-Override", "DELETE");
		// TODO other headers?

		return builder;

	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#getEntry(java.lang.String)
	 */
	public Deferred<AtomEntry> getEntry(String entryURL) {
		
		Deferred<AtomEntry> deferredResult = new Deferred<AtomEntry>();

		RequestBuilder requestBuilder = buildGetEntryRequest(entryURL);
		
		requestBuilder.setCallback(new GetEntryCallback(factory, deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}
	
	
	
	
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

	
	
	public static RequestBuilder buildGetEntryRequest(String entryURL) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(entryURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		
		return builder;

	}
	
	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#getFeed(java.lang.String)
	 */
	public Deferred<AtomFeed> getFeed(String feedURL) {

		Deferred<AtomFeed> deferredResult = new Deferred<AtomFeed>();

		RequestBuilder requestBuilder = buildGetFeedRequest(feedURL);
		
		requestBuilder.setCallback(new GetFeedCallback(factory, deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	public static RequestBuilder buildGetFeedRequest(String feedURL) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(feedURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		
		return builder;

	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#postEntry(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)
	 */
	public Deferred<AtomEntry> postEntry(String feedURL, AtomEntry entry) {

		Deferred<AtomEntry> deferredResult = new Deferred<AtomEntry>();

		RequestBuilder requestBuilder = buildPostEntryRequest(feedURL, entry);
		
		requestBuilder.setCallback(new PostEntryCallback(factory, deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	public static RequestBuilder buildPostEntryRequest(String feedURL, AtomEntry entry) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = entry.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#putEntry(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)
	 */
	public Deferred<AtomEntry> putEntry(String entryURL, AtomEntry entry) {

		Deferred<AtomEntry> deferredResult = new Deferred<AtomEntry>();

		RequestBuilder requestBuilder = buildPutEntryRequest(entryURL, entry);
		
		requestBuilder.setCallback(new PutEntryCallback(factory, deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	public static RequestBuilder buildPutEntryRequest(String entryURL, AtomEntry entry) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(entryURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = entry.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setHeader("X-HTTP-Method-Override", "PUT");
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}
	
	
	
}
