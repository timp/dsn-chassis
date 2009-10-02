/**
 * 
 */
package org.cggh.chassis.generic.atom.exist.client.protocol.impl;

import org.cggh.chassis.generic.atom.exist.client.protocol.ExistAtomService;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.CallbackWithNoContent;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.HttpDeferred;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;

/**
 * @author aliman
 *
 */
public class ExistAtomServiceImpl extends AtomServiceImpl implements
		ExistAtomService {

	/**
	 * @param factory
	 */
	public ExistAtomServiceImpl(AtomFactory factory) {
		super(factory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.exist.client.protocol.ExistAtomService#postFeed(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed)
	 */
	public Deferred<Void> postFeed(String feedUrl, AtomFeed feed) {

		HttpDeferred<Void> deferredResult = new HttpDeferred<Void>();

		RequestBuilder requestBuilder = buildPostFeedRequest(feedUrl, feed);
		
		requestBuilder.setCallback(new CallbackWithNoContent(deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	/**
	 * @param feedUrl
	 * @param feed
	 * @return
	 */
	public static RequestBuilder buildPostFeedRequest(String feedUrl, AtomFeed feed) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedUrl));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=feed;charset=\"utf-8\"");
		String content = feed.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.exist.client.protocol.ExistAtomService#putFeed(java.lang.String, org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed)
	 */
	public Deferred<Void> putFeed(String feedUrl, AtomFeed feed) {

		HttpDeferred<Void> deferredResult = new HttpDeferred<Void>();

		RequestBuilder requestBuilder = buildPutFeedRequest(feedUrl, feed);
		
		requestBuilder.setCallback(new CallbackWithNoContent(deferredResult));

		sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}

	/**
	 * @param feedUrl
	 * @param feed
	 * @return
	 */
	public static RequestBuilder buildPutFeedRequest(String feedUrl, AtomFeed feed) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedUrl));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=feed;charset=\"utf-8\"");
		String content = feed.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setHeader("X-HTTP-Method-Override", "PUT");
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}

}
