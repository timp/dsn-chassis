/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.exist;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.HttpDeferred;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atom.client.AtomFeed;
import org.cggh.chassis.generic.atom.client.AtomServiceImpl;
import org.cggh.chassis.generic.atom.client.CallbackWithNoContent;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;

/**
 * @author aliman
 *
 */
public class ExistAtomService
	<E extends AtomEntry, F extends AtomFeed<E>>
	extends AtomServiceImpl<E, F> {


	
	
	
	/**
	 * @param factory
	 */
	public ExistAtomService(AtomFactory<E, F> factory) {
		super(factory);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.exist.client.protocol.ExistAtomService#postFeed(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed)
	 */
	public Deferred<Void> postFeed(String feedUrl, F feed) {

		HttpDeferred<Void> deferredResult = new HttpDeferred<Void>();

		RequestBuilder requestBuilder = buildPostFeedRequest(feedUrl, feed.toString());
		
		requestBuilder.setCallback(new CallbackWithNoContent(deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	/**
	 * @param feedUrl
	 * @param feed
	 * @return
	 */
	public static RequestBuilder buildPostFeedRequest(String feedUrl, String content) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedUrl));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=feed;charset=\"utf-8\"");
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.exist.client.protocol.ExistAtomService#putFeed(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed)
	 */
	public Deferred<Void> putFeed(String feedUrl, F feed) {

		HttpDeferred<Void> deferredResult = new HttpDeferred<Void>();

		RequestBuilder requestBuilder = buildPutFeedRequest(feedUrl, feed.toString());
		
		requestBuilder.setCallback(new CallbackWithNoContent(deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	
	
	
	/**
	 * @param feedUrl
	 * @param feed
	 * @return
	 */
	public static RequestBuilder buildPutFeedRequest(String feedUrl, String content) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedUrl));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=feed;charset=\"utf-8\"");
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setHeader("X-HTTP-Method-Override", "PUT");
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}
	
	
	

}
