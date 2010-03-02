/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;



/**
 * @author lee
 *
 */
public class FilesToReviewWidgetController {

	private static final Log log = LogFactory.getLog(FilesToReviewWidgetController.class);	
	
	private FilesToReviewWidgetModel model;
	private FilesToReviewWidget owner;	

	/**
	 * @param owner
	 * @param model
	 */
	public FilesToReviewWidgetController(
			FilesToReviewWidget owner,
			FilesToReviewWidgetModel model) {
		
		this.owner = owner;
		this.model = model;

	}


	public Deferred<Document> retrieveFilesToReview() {
		
		log.enter("retrieveFilesToReview");
		
		log.debug("setting status to 'async request pending'...");		
		model.setStatus(FilesToReviewWidgetModel.STATUS_RETRIEVE_FILES_TO_REVIEW_PENDING);
		
		String url = Config.get(Config.QUERY_FILESTOREVIEW_URL);
		log.debug("getting URL: " + url);
		
		log.debug("getting feed from URL...");
		Deferred<Document> deferredFilesToReviewFeedDoc = Atom.getFeed(url);
		
		deferredFilesToReviewFeedDoc.addCallback(new RetrieveFilesToReviewCallback());
		deferredFilesToReviewFeedDoc.addErrback(new DefaultErrback());
		
		log.leave();
		
		return deferredFilesToReviewFeedDoc;
	}

	public class RetrieveFilesToReviewCallback implements Function<Document, Document> {


		public Document apply(Document filesToReviewFeedDoc) {
			
			model.filesToReviewFeedDoc.set(filesToReviewFeedDoc);
			
			model.setStatus(FilesToReviewWidgetModel.STATUS_FILES_TO_REVIEW_RETRIEVED);

			return filesToReviewFeedDoc;
		}

	}

	public void selectFileToBeReviewedEntryElement(Element fileToBeReviewedEntryElement) {
		
		log.enter("selectFileToBeReviewedEntryElement");
		
		model.setFileToBeReviewedEntryElement(fileToBeReviewedEntryElement);
		
		log.leave();
	}	
	
	
	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(FilesToReviewWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}



}
