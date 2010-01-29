/**
 * 
 */
package org.cggh.chassis.generic.miniatom.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.HttpDeferred;
import org.cggh.chassis.generic.async.client.HttpHelper;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.URL;
import com.google.gwt.xml.client.Document;

/**
 * @author aliman
 *
 */
public class Atom {

	
	
	
	
	public static final String NSURI = "http://www.w3.org/2005/Atom";
	public static final String PREFIX = "atom";
	public static final String ELEMENT_AUTHOR = "author";
	public static final String ELEMENT_NAME = "name";
	public static final String ELEMENT_EMAIL = "email";
	public static final String ELEMENT_URI = "uri";
	public static final String ELEMENT_CATEGORY = "category";
	public static final String ELEMENT_LINK = "link";
	public static final String ELEMENT_ENTRY = "entry";
	public static final String ELEMENT_TITLE = "title";
	public static final String ELEMENT_SUMMARY = "summary";
	public static final String ELEMENT_SIZE = "size";
	public static final String ELEMENT_ID = "id";
	public static final String ELEMENT_PUBLISHED = "published";
	public static final String ELEMENT_UPDATED = "updated";
	public static final String ELEMENT_FEED = "feed";
	public static final String ELEMENT_CONTENT = "content";
	public static final String REL_EDIT = "edit";
	public static final String REL_EDIT_MEDIA = "edit-media";
	public static final String ATTR_HREF = "href";
	public static final String ATTR_HREFLANG = "hreflang";
	public static final String ATTR_LENGTH = "length";
	public static final String ATTR_REL = "rel";
	public static final String ATTR_TITLE = "title";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_LABEL = "label";
	public static final String ATTR_TERM = "term";
	public static final String ATTR_SCHEME = "scheme";
	public static final String ATTR_SRC = "src";

	
	
	
	public static Deferred<Document> getEntry(String url) {
		
		HttpDeferred<Document> deferredResult = new HttpDeferred<Document>();
		
		RequestBuilder requestBuilder = buildGetEntryRequest(url);
		
		requestBuilder.setCallback(new GetEntryCallback(deferredResult));
		
		HttpHelper.sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;
	}
	
	
	
	
	protected static RequestBuilder buildGetEntryRequest(String url) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		
		return builder;

	}
	
	
	
	
	public static Deferred<Document> getFeed(String url) {
		
		HttpDeferred<Document> deferredResult = new HttpDeferred<Document>();
		
		RequestBuilder requestBuilder = buildGetFeedRequest(url);
		
		requestBuilder.setCallback(new GetFeedCallback(deferredResult));
		
		HttpHelper.sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;
	}
	
	
	
	
	protected static RequestBuilder buildGetFeedRequest(String url) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		
		return builder;

	}
	
	
	
	
	public static Deferred<Document> postEntry(String url, Document entryDoc) {

		HttpDeferred<Document> deferredResult = new HttpDeferred<Document>();
		
		RequestBuilder requestBuilder = buildPostEntryRequest(url, entryDoc.toString());
		
		requestBuilder.setCallback(new PostEntryCallback(deferredResult));
		
		HttpHelper.sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}
	
	
	
	protected static RequestBuilder buildPostEntryRequest(String feedURL, String content) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}
	
	
	
	
	
	public static Deferred<Document> putEntry(String url, Document entryDoc) {

		HttpDeferred<Document> deferredResult = new HttpDeferred<Document>();
		
		RequestBuilder requestBuilder = buildPutEntryRequest(url, entryDoc.toString());
		
		requestBuilder.setCallback(new PutEntryCallback(deferredResult));
		
		HttpHelper.sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}
	
	
	
	protected static RequestBuilder buildPutEntryRequest(String feedURL, String content) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(feedURL));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setHeader("X-HTTP-Method-Override", "PUT");
		// TODO other headers?
		
		builder.setRequestData(content);

		return builder;

	}
	
	
	
	
	public static Deferred<Void> deleteEntry(String url) {

		HttpDeferred<Void> deferredResult = new HttpDeferred<Void>();
		
		RequestBuilder requestBuilder = buildDeleteEntryRequest(url);
		
		requestBuilder.setCallback(new DeleteEntryCallback(deferredResult));
		
		HttpHelper.sendRequest(requestBuilder, deferredResult);
		
		return deferredResult;

	}
	
	
	

	protected static RequestBuilder buildDeleteEntryRequest(String url) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));		
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("X-HTTP-Method-Override", "DELETE");
		// TODO other headers?

		return builder;

	}
	
	
	
	

	

}
