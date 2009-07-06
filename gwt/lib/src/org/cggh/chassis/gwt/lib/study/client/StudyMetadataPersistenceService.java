/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyMetadataPersistenceService {

	/**
	 * TODO doc me
	 */
	public StudyMetadataPersistenceService() {
		// TODO
	}
	
	/**
	 * 
	 * TODO document me
	 * 
	 * @param collectionURL
	 */
	public void setCollectionURL(String collectionURL) {
		
	}
	
	public void getFeed(CallbackWithStudyFeed callback) {
		// TODO
	}
	
	public void getEntry(String entryURL, CallbackWithStudyEntry callback) {
		// TODO
	}
	
	public void postEntry(StudyEntry study, CallbackWithStudyEntry callback) {
		// TODO
	}
	
	public void putEntry(String entryURL, StudyEntry study, CallbackWithStudyEntry callback) {
		// TODO
	}
	
	public void deleteEntry(String entryURL, CallbackWithNoContent callback) {
		// TODO
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

		public void onSuccess(Request request, Response response, StudyEntry feed);
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
