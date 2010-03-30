
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;

/**
 * @author timp
 */
public class ListStudiesWidgetController {
	
	
	private Log log = LogFactory.getLog(ListStudiesWidgetController.class);
	
	
	private ListStudiesWidget owner;
	private ListStudiesWidgetModel model;



			
	public ListStudiesWidgetController(ListStudiesWidget owner, ListStudiesWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	public Deferred<Document> retrieveStudies() {
		log.enter("retrieveStudies");
		
		log.debug("set the model's status to pending");
		model.status.set(ListStudiesWidgetModel.STATUS_RETRIEVE_STUDY_FEED_PENDING);
		
		// expected to be /curator/query/studies.xql
		String studyQueryServiceUrl = Config.get(Config.COLLECTION_STUDIES_URL);
		Deferred<Document> deferredDoc = Atom.getFeed(studyQueryServiceUrl);

		// Add a call-back and error-back for the asynchronous feed.
		deferredDoc.addCallback(new RetrieveStudiesCallback());
		deferredDoc.addErrback(new DefaultErrback());
		
		log.leave();
		return deferredDoc;
	}
	

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.status.set(AsyncWidgetModel.STATUS_ERROR);
			model.message.set(in.getMessage());
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}


	
	private class RetrieveStudiesCallback implements Function<Document, Document> {

		@Override
		public Document apply(Document in) {
			log.debug("RetrieveStudiesCallback.apply setting model status to " + ListStudiesWidgetModel.STATUS_READY_FOR_INTERACTION);
			model.status.set(ListStudiesWidgetModel.STATUS_READY_FOR_INTERACTION);
			model.studyFeed.set(in);
			return in;
		}

	}




}
