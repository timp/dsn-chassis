
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class ListStudiesWidgetController {
	
	
	private Log log = LogFactory.getLog(ListStudiesWidgetController.class);
	
	
	private ListStudiesWidget owner;
	private ListStudiesWidgetModel model;


	private static String STUDIES_QUERY = "/curator/query/studies.xql";

			
	public Deferred<Document> retrieveStudies() {
		log.enter("retrieveStudies");
		// Set the model's status to pending.
		//model.setStatus(ListStudiesWidgetModel.STATUS_RETRIEVE_STUDIES_PENDING);
		
		String studyQueryServiceUrl = Config.get(Config.QUERY_STUDIES_URL);
		
		Deferred<Document> deferredDoc = Atom.getFeed(studyQueryServiceUrl);
		
		// Add a call-back and error-back for the asynchronous feed.
		deferredDoc.addCallback(new RetrieveStudiesCallback());
		deferredDoc.addErrback(new DefaultErrback());
		
		log.leave();
		return deferredDoc;
	}
	
	
	
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();
	/*	
		if (model.submissionId.get() != null) {
			
			Deferred<Element> chain = retrieveSubmission();
			
			chain.addCallback(new RetrieveSubmissionCallback());
			
			chain.addCallback(new RetrieveQuestionnaireCallback());
			
			chain.addCallback(retrieveStudyCallback);

			// handle errors
			chain.addErrback(new DefaultErrback());
			
			// finally callback with owner, in any case
			chain.addBoth(new Function<Object, Object>() {

				public Object apply(Object in) {
					deferredOwner.callback(owner);
					return in;
				}
				
			});

		}
		*/
		log.leave();
		return deferredOwner;
		
	}

	
	
		

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.status.set(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}




	
	private class RetrieveStudiesCallback implements Function<Element, Deferred<Document>> {

		public Deferred<Document> apply(Element studiesEntryElement) {
			
			// model.setStudiesEntryElement(studiesEntryElement);
			
			Deferred<Document> deferredFilesFeedDoc = null;
			
			if (studiesEntryElement != null) {

				// we have one, so lets try retrieving the list of files uploaded so far
				// deferredFilesFeedDoc = retrieveFiles();
				
			}
			
			else {
				
				//model.setStatus(ListStudiesWidgetModel.STATUS_STUDY_NOT_FOUND);
				deferredFilesFeedDoc = new Deferred<Document>();
				deferredFilesFeedDoc.callback(null);

			}
			
			// return the deferred feed of files uploaded, for chaining of callbacks
			return deferredFilesFeedDoc;
		}
		
	}
	
	
        					
  private ViewStudyNavigationEvent viewStudyNavigationEvent;
	
         	     	   	 
	
	
	public ListStudiesWidgetController(ListStudiesWidget owner, ListStudiesWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	

}
