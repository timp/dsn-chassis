/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

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

	
	
	/**
	 * TODO doc me
	 */
	public StudyMetadataPersistenceService(String collectionURL) {
		this.collectionURL = collectionURL;
	}
	
	
	
	public void getFeed(final CallbackWithStudyFeed callback) throws RequestException {
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(collectionURL));
		
		RequestCallback chain = new RequestCallback() {

			
			
			public void onError(Request request, Throwable exception) {
				
				// pass through to next callback
				callback.onError(request, exception);

			}

			
			
			public void onResponseReceived(Request request, Response response) {
				
				if (response.getStatusCode() == 200) {
					
					String contentType = response.getHeader("Content-Type");
					
					if (contentType.startsWith("application/atom+xml") || contentType.startsWith("application/xml")) {
						
						// wrap response 
						StudyFeed feed = new StudyFeed(response.getText());

						// pass through to next callback
						callback.onSuccess(request, response, feed);

					}
					else {

						// pass through as error
						callback.onError(request, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));

					}
				}
				else {

					// pass through to next callback
					callback.onFailure(request, response);

				}
				
			}
			
			
			
		};
		
		builder.setCallback(chain);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		builder.send();
		
	}
	
	
	
	public void getEntry(String entryURL, final CallbackWithStudyEntry callback) throws RequestException {

		if (!entryURL.startsWith(collectionURL)) {
			// assume entryURL is relative to collection URL
			entryURL = collectionURL + entryURL;
		}
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(entryURL));
		
		RequestCallback chain = new RequestCallback() {

			public void onError(Request request, Throwable exception) {

				// pass through to next callback
				callback.onError(request, exception);
				
			}

			public void onResponseReceived(Request request, Response response) {

				if (response.getStatusCode() == 200) {
					
					String contentType = response.getHeader("Content-Type");
					
					if (contentType.startsWith("application/atom+xml") || contentType.startsWith("application/xml")) {
						
						// wrap response 
						StudyEntry entry = new StudyEntry(response.getText());

						// pass through to next callback
						callback.onSuccess(request, response, entry);

					}
					else {

						// pass through as error
						callback.onError(request, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));

					}
				}
				else {

					// pass through to next callback
					callback.onFailure(request, response);

				}				
				
			}
			
		};
		
		builder.setCallback(chain);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		// TODO any other headers?
		builder.send();
				
	}
	
	
	
	public void postEntry(StudyEntry study, final CallbackWithStudyEntry callback) throws RequestException {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(collectionURL));
		
		RequestCallback chain = new RequestCallback() {

			public void onError(Request request, Throwable exception) {

				// pass through to next callback
				callback.onError(request, exception);
				
			}

			public void onResponseReceived(Request request, Response response) {

				if (response.getStatusCode() == 201) {
					
					String contentType = response.getHeader("Content-Type");
					
					if (contentType.startsWith("application/atom+xml") || contentType.startsWith("application/xml")) {
						
						// wrap response 
						StudyEntry entry = new StudyEntry(response.getText());

						// pass through to next callback
						callback.onSuccess(request, response, entry);

					}
					else {

						// pass through as error
						callback.onError(request, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));

					}
				}
				else {

					// pass through to next callback
					callback.onFailure(request, response);

				}				
				
			}
			
		};
		
		builder.setCallback(chain);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = study.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setRequestData(content);
		// TODO any other headers?
		builder.send();
				
	}
	
	
	
	public void putEntry(String entryURL, StudyEntry study, final CallbackWithStudyEntry callback) throws RequestException {
		
		if (!entryURL.startsWith(collectionURL)) {
			// assume entryURL is relative to collection URL
			entryURL = collectionURL + entryURL;
		}
				
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(entryURL));
		
		RequestCallback chain = new RequestCallback() {

			public void onError(Request request, Throwable exception) {

				// pass through to next callback
				callback.onError(request, exception);
				
			}

			public void onResponseReceived(Request request, Response response) {

				if (response.getStatusCode() == 200) {
					
					String contentType = response.getHeader("Content-Type");
					
					if (contentType.startsWith("application/atom+xml") || contentType.startsWith("application/xml")) {
						
						// wrap response 
						StudyEntry entry = new StudyEntry(response.getText());

						// pass through to next callback
						callback.onSuccess(request, response, entry);

					}
					else {

						// pass through as error
						callback.onError(request, new Exception("expected content type starts with \"application/atom+xml\" or \"application/xml\", found \""+contentType+"\""));

					}
				}
				else {

					// pass through to next callback
					callback.onFailure(request, response);

				}				
				
			}
			
		};
		
		builder.setCallback(chain);
		builder.setHeader("Accept", "application/atom+xml,application/xml");
		builder.setHeader("Content-Type", "application/atom+xml;type=entry;charset=\"utf-8\"");
		String content = study.toString();
		builder.setHeader("Content-Length", Integer.toString(content.length()));
		builder.setRequestData(content);
		builder.setHeader("X-HTTP-Method-Override","PUT"); // needed because GWT doesn't support PUT
		// TODO any other headers?
		builder.send();
		
	}
	
	
	public void deleteEntry(String entryURL, final CallbackWithNoContent callback) throws RequestException {

		if (!entryURL.startsWith(collectionURL)) {
			// assume entryURL is relative to collection URL
			entryURL = collectionURL + entryURL;
		}
			
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(entryURL));
		
		RequestCallback chain = new RequestCallback() {

			public void onError(Request request, Throwable exception) {

				// pass through to next callback
				callback.onError(request, exception);
				
			}

			public void onResponseReceived(Request request, Response response) {

				if (response.getStatusCode() == 204) {
					
					// pass through to next callback
					callback.onSuccess(request, response);

				}
				else {

					// pass through to next callback
					callback.onFailure(request, response);

				}				
				
			}
			
		};
		
		builder.setCallback(chain);
		builder.setHeader("X-HTTP-Method-Override","DELETE"); // needed because GWT doesn't support DELETE
		// TODO any other headers?
		builder.send();
	}
	
	
	
	/**
	 * 
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public interface CallbackWithStudyFeed {
		
		public void onSuccess(Request request, Response response, StudyFeed feed);
		public void onFailure(Request request, Response response);
		public void onError(Request request, Throwable exception);
		
	}
	
	/**
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public interface CallbackWithStudyEntry {

		public void onSuccess(Request request, Response response, StudyEntry entry);
		public void onFailure(Request request, Response response);
		public void onError(Request request, Throwable exception);

	}

	/**
	 * TODO document me
	 * 
	 * @author aliman
	 *
	 */
	public interface CallbackWithNoContent {

		public void onSuccess(Request request, Response response);
		public void onFailure(Request request, Response response);
		public void onError(Request request, Throwable exception);

	}

}
