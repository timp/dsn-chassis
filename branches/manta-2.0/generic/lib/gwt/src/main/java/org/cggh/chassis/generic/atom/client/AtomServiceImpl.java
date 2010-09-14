/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.HttpCanceller;
import org.cggh.chassis.generic.async.client.HttpDeferred;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;

/**
 * @author aliman
 *
 */
public class AtomServiceImpl
	<E extends AtomEntry, F extends AtomFeed<E>> 
	implements AtomService<E, F> {

	
	
	protected AtomFactory<E, F> factory;
	private String baseUrl = null;




	public AtomServiceImpl(AtomFactory<E, F> factory) {
		this.factory = factory;
	}
	
	
	
	
	public AtomServiceImpl(AtomFactory<E, F> factory, String baseUrl) {
		this.factory = factory;
		this.baseUrl  = baseUrl;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#deleteEntry(java.lang.String)
	 */
	public Deferred<Void> deleteEntry(String entryUrl) {
		
		if (baseUrl != null) entryUrl = baseUrl + entryUrl;

		HttpDeferred<Void> deferredResult = new HttpDeferred<Void>();

		RequestBuilder requestBuilder = buildDeleteEntryRequest(entryUrl);
		
		requestBuilder.setCallback(new CallbackWithNoContent(deferredResult));

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
	public Deferred<E> getEntry(String entryUrl) {
		
		if (baseUrl != null) entryUrl = baseUrl + entryUrl;

		HttpDeferred<E> deferredResult = new HttpDeferred<E>();

		RequestBuilder requestBuilder = buildGetEntryRequest(entryUrl);
		
		requestBuilder.setCallback(new GetEntryCallback<E, F>(factory, deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}
	
	
	
	
	/**
	 * @param requestBuilder
	 * @param deferredResult
	 */
	@SuppressWarnings("unchecked")
	protected static void sendRequest(RequestBuilder requestBuilder, HttpDeferred deferredResult) {

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
	public Deferred<F> getFeed(String feedUrl) {

		if (baseUrl != null) feedUrl = baseUrl + feedUrl;

		HttpDeferred<F> deferredResult = new HttpDeferred<F>();

		RequestBuilder requestBuilder = buildGetFeedRequest(feedUrl);
		
		requestBuilder.setCallback(new GetFeedCallback<E, F>(factory, deferredResult));

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
	public Deferred<E> postEntry(String feedUrl, E entry) {

		if (baseUrl != null) feedUrl = baseUrl + feedUrl;

		HttpDeferred<E> deferredResult = new HttpDeferred<E>();

		RequestBuilder requestBuilder = buildPostEntryRequest(feedUrl, entry.toString());
		
		requestBuilder.setCallback(new PostEntryCallback<E, F>(factory, deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	public static RequestBuilder buildPostEntryRequest(String feedURL, String content) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#putEntry(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry)
	 */
	public Deferred<E> putEntry(String entryUrl, E entry) {

		if (baseUrl != null) entryUrl = baseUrl + entryUrl;

		HttpDeferred<E> deferredResult = new HttpDeferred<E>();

		RequestBuilder requestBuilder = buildPutEntryRequest(entryUrl, entry.toString());
		
		requestBuilder.setCallback(new PutEntryCallback<E, F>(factory, deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	public static RequestBuilder buildPutEntryRequest(String entryURL, String content) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(entryURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setHeader("X-HTTP-Method-Override", "PUT");
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}
	
	
	
}
