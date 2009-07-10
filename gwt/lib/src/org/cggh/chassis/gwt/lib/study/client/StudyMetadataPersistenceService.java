/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;


import org.cggh.chassis.gwt.lib.atom.client.AtomFormatException;
import org.cggh.chassis.gwt.lib.atom.client.CallbackWithNoContent;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;


/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyMetadataPersistenceService {
	
	private String collectionURL;

	public StudyMetadataPersistenceService(String collectionURL) {
		this.collectionURL = collectionURL;
	}
	
	
	
	/**
	 * TODO document me
	 * 
	 * @param callback
	 * @throws RequestException
	 */
	public void getFeed(CallbackWithStudyFeed callback) throws RequestException {
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(collectionURL));		
		RequestCallback glue = new GetFeedRequestCallback(callback);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		builder.send();

	}
	
	
	
	/**
	 * TODO document me
	 * 
	 * @param entryURL
	 * @param callback
	 * @throws RequestException
	 */
	public void getEntry(String entryURL, CallbackWithStudyEntry callback) throws RequestException {

		if (!entryURL.startsWith(collectionURL)) {
			// assume entryURL is relative to collection URL
			entryURL = collectionURL + entryURL; // TODO this is probably broken
		}
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(entryURL));
		RequestCallback glue = new GetEntryRequestCallback(callback);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		builder.send();
	}
	
	
	
	/**
	 * TODO document me
	 * 
	 * @param entry
	 * @param callback
	 * @throws RequestException 
	 */
	public void postEntry(StudyEntry entry, CallbackWithStudyEntry callback) throws RequestException {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(collectionURL));		
		RequestCallback glue = new PostEntryRequestCallback(callback);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = entry.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setRequestData(content);
		// TODO any other headers?
		builder.send();

	}
	
	
	
	/**
	 * TODO document me
	 * 
	 * @param entryURL
	 * @param entry
	 * @param callback
	 * @throws RequestException 
	 */
	public void putEntry(String entryURL, StudyEntry entry, CallbackWithStudyEntry callback) throws RequestException {

		if (!entryURL.startsWith(collectionURL)) {
			// assume entryURL is relative to collection URL
			entryURL = collectionURL + entryURL;
		}
				
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(entryURL));		
		RequestCallback glue = new PutEntryRequestCallback(callback);
		builder.setCallback(glue);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = entry.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setRequestData(content);
		builder.setHeader("X-HTTP-Method-Override","PUT"); // needed because GWT doesn't support PUT
		// TODO any other headers?
		builder.send();
		
	}
	
	
	
	/**
	 * TODO document me
	 * 
	 * @param entryURL
	 * @param callback
	 * @throws RequestException 
	 */
	public void deleteEntry(String entryURL, CallbackWithNoContent callback) throws RequestException {

		if (!entryURL.startsWith(collectionURL)) {
			// assume entryURL is relative to collection URL
			entryURL = collectionURL + entryURL;
		}
			
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(entryURL));		
		RequestCallback glue = new DeleteEntryRequestCallback(callback);
		builder.setCallback(glue);
		builder.setHeader("X-HTTP-Method-Override","DELETE"); // needed because GWT doesn't support DELETE
		// TODO any other headers?
		builder.send();
		
	}

	
	
	/**
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public class GetFeedRequestCallback implements RequestCallback {

		private CallbackWithStudyFeed next;

		public GetFeedRequestCallback(CallbackWithStudyFeed next) {
			this.next = next;
		}

		public void onError(Request request, Throwable exception) {
			// pass through to next callback
			next.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {

			// precondition: check status code
			if (response.getStatusCode() != 200) {
				// pass through to next callback
				next.onFailure(request, response);
				return;
			}
			
			// precondition: check content type
			String contentType = response.getHeader("Content-Type");
			if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
				// pass through as error
				next.onError(request, response, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));
				return;
			}
				
			StudyFeed feed = null;
			
			// parse the response
			try {

				feed = new StudyFeed(response.getText()); 
				
			} catch (AtomFormatException ex) {
				// pass through as error
				next.onError(request, response, ex);
				return;
			}

			// pass through to next callback
			next.onSuccess(request, response, feed);		
			
		}		

	}

	

	/**
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public class GetEntryRequestCallback implements RequestCallback {

		private CallbackWithStudyEntry next;

		public GetEntryRequestCallback(CallbackWithStudyEntry next) {
			this.next = next;
		}

		public void onError(Request request, Throwable exception) {
			// pass through to next callback
			next.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			
			// precondition: check status code
			if (response.getStatusCode() != 200) {
				// pass through to next callback
				next.onFailure(request, response);
				return;
			}
			
			// precondition: check content type
			String contentType = response.getHeader("Content-Type");
			if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
				// pass through as error
				next.onError(request, response, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));
				return;
			}
				
			StudyEntry entry = null;
			
			// parse the response
			try {

				entry = new StudyEntry(response.getText()); 
				
			} catch (AtomFormatException ex) {
				// pass through as error
				next.onError(request, response, ex);
				return;
			}

			// pass through to next callback
			next.onSuccess(request, response, entry);

		}

	}

	
	
	/**
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public class PostEntryRequestCallback implements RequestCallback {

		private CallbackWithStudyEntry next;

		public PostEntryRequestCallback(CallbackWithStudyEntry next) {
			this.next = next;
		}

		public void onError(Request request, Throwable exception) {
			// pass through to next callback
			next.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {

			// precondition: check status code
			if (response.getStatusCode() != 201) {
				// pass through to next callback
				next.onFailure(request, response);
				return;
			}
			
			// precondition: check content type
			String contentType = response.getHeader("Content-Type");
			if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
				// pass through as error
				next.onError(request, response, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));
				return;
			}
				
			StudyEntry entry = null;

			// parse the response
			try {

				entry = new StudyEntry(response.getText()); 
				
			} catch (AtomFormatException ex) {
				// pass through as error
				next.onError(request, response, ex);
				return;
			}

			// pass through to next callback
			next.onSuccess(request, response, entry);
	
		}

	}

	
	
	/**
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public class PutEntryRequestCallback implements RequestCallback {

		private CallbackWithStudyEntry next;

		public PutEntryRequestCallback(CallbackWithStudyEntry next) {
			this.next = next;
		}

		public void onError(Request request, Throwable exception) {
			// pass through to next callback
			next.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			
			// precondition: check status code
			if (response.getStatusCode() != 200) {
				// pass through to next callback
				next.onFailure(request, response);
				return;
			}
			
			// precondition: check content type
			String contentType = response.getHeader("Content-Type");
			if (!contentType.startsWith("application/atom+xml") && !contentType.startsWith("application/xml")) {
				// pass through as error
				next.onError(request, response, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));
				return;
			}
				
			StudyEntry entry = null;
			
			// parse the response
			try {

				entry = new StudyEntry(response.getText()); 
				
			} catch (AtomFormatException ex) {
				// pass through as error
				next.onError(request, response, ex);
				return;
			}

			// pass through to next callback
			next.onSuccess(request, response, entry);

		}

	}

	
	
	/**
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public class DeleteEntryRequestCallback implements RequestCallback {

		private CallbackWithNoContent next;

		public DeleteEntryRequestCallback(CallbackWithNoContent next) {
			this.next = next;
		}

		public void onError(Request request, Throwable exception) {
			// pass through to next callback
			next.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {

			// precondition: check status code
			if (response.getStatusCode() != 204) {
				// pass through to next callback
				next.onFailure(request, response);
				return;
			}

			// pass through to next callback
			next.onSuccess(request, response);

		}

	}

	
	
}
