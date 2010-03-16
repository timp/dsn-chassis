
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
public class ViewStudyQuestionnaireWidgetController {
	
	
	private Log log = LogFactory.getLog(ViewStudyQuestionnaireWidgetController.class);
	
	
	private ViewStudyQuestionnaireWidget owner;
	private ViewStudyQuestionnaireWidgetModel model;


	private static String STUDY_ENTRY_URI = "/atom/edit/studies/id.atom";

			
	public Deferred<Document> retrieveStudy() {
		log.enter("retrieveStudy");
		// Set the model's status to pending.
		//model.setStatus(ViewStudyQuestionnaireWidgetModel.STATUS_RETRIEVE_STUDIES_PENDING);
		
		String studyQueryServiceUrl = Config.get(Config.QUERY_STUDIES_URL);
		
		Deferred<Document> deferredDoc = Atom.getFeed(studyQueryServiceUrl);
		
		// Add a call-back and error-back for the asynchronous feed.
		deferredDoc.addCallback(new RetrieveStudyCallback());
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




	
	private class RetrieveStudyCallback implements Function<Element, Deferred<Document>> {

		public Deferred<Document> apply(Element studyEntryElement) {
			
			// model.setStudyEntryElement(studyEntryElement);
			
			Deferred<Document> deferredFilesFeedDoc = null;
			
			if (studyEntryElement != null) {

				// we have one, so lets try retrieving the list of files uploaded so far
				// deferredFilesFeedDoc = retrieveFiles();
				
			}
			
			else {
				
				//model.setStatus(ViewStudyQuestionnaireWidgetModel.STATUS_STUDY_NOT_FOUND);
				deferredFilesFeedDoc = new Deferred<Document>();
				deferredFilesFeedDoc.callback(null);

			}
			
			// return the deferred feed of files uploaded, for chaining of callbacks
			return deferredFilesFeedDoc;
		}
		
	}
	
	
        
	private StudySummaryWidget studySummaryWidget;

 
	private ViewQuestionnaireWidget viewQuestionnaireWidget;

 
	
	
	public ViewStudyQuestionnaireWidgetController(ViewStudyQuestionnaireWidget owner, ViewStudyQuestionnaireWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	

}
