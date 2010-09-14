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
public class FilesToCleanWidgetController {

	private static final Log log = LogFactory.getLog(FilesToCleanWidgetController.class);	
		
	
	private FilesToCleanWidgetModel model;
	private FilesToCleanWidget owner;	

	
	// Allow this controller to construct itself, defining its owner and model.
	/**
	 * @param owner
	 * @param model
	 */
	public FilesToCleanWidgetController(
			FilesToCleanWidget owner,
			FilesToCleanWidgetModel model) {
		
		this.owner = owner;
		this.model = model;

	}


	public Deferred<Document> retrieveFilesToClean() {
		
		log.enter("retrieveFilesToClean");
		
		log.debug("setting status to 'async request pending'...");		
		model.setStatus(FilesToCleanWidgetModel.STATUS_RETRIEVE_FILES_TO_CLEAN_PENDING);
		
		String url = Config.get(Config.QUERY_FILESTOCLEAN_URL);
		log.debug("getting URL: " + url);
		
		log.debug("getting feed from URL...");
		Deferred<Document> deferredFilesToCleanFeedDoc = Atom.getFeed(url);
		
		deferredFilesToCleanFeedDoc.addCallback(new RetrieveFilesToCleanCallback());
		deferredFilesToCleanFeedDoc.addErrback(new DefaultErrback());
		
		log.leave();
		
		return deferredFilesToCleanFeedDoc;
	}
	
	public class RetrieveFilesToCleanCallback implements Function<Document, Document> {


		public Document apply(Document filesToCleanFeedDoc) {
			
			model.filesToCleanFeedDoc.set(filesToCleanFeedDoc);
			
			model.setStatus(FilesToCleanWidgetModel.STATUS_FILES_TO_CLEAN_RETRIEVED);

			return filesToCleanFeedDoc;
		}

	}

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(FilesToCleanWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}

	public void selectFileToBeCleanedEntryElement(Element fileToBeCleanedEntryElement) {
		
		log.enter("selectFileToBeCleanedEntryElement");
		
		model.setFileToBeCleanedEntryElement(fileToBeCleanedEntryElement);
		
		log.leave();
		
	}


}
