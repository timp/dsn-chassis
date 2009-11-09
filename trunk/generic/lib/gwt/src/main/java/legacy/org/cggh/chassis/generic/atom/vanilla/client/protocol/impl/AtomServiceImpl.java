/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

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
public class AtomServiceImpl implements AtomService {

	
	
	protected AtomFactory factory;




	public AtomServiceImpl(AtomFactory factory) {
		this.factory = factory;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService#deleteEntry(java.lang.String)
	 */
	public Deferred<Void> deleteEntry(String entryURL) {

		HttpDeferred<Void> deferredResult = new HttpDeferred<Void>();

		RequestBuilder requestBuilder = buildDeleteEntryRequest(entryURL);
		
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
	public Deferred<AtomEntry> getEntry(String entryURL) {
		
		HttpDeferred<AtomEntry> deferredResult = new HttpDeferred<AtomEntry>();

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
	public Deferred<AtomFeed> getFeed(String feedURL) {

		HttpDeferred<AtomFeed> deferredResult = new HttpDeferred<AtomFeed>();

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

		HttpDeferred<AtomEntry> deferredResult = new HttpDeferred<AtomEntry>();

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

		HttpDeferred<AtomEntry> deferredResult = new HttpDeferred<AtomEntry>();

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
