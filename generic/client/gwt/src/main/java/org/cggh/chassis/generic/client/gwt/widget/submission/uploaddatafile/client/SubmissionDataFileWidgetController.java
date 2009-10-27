/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * @author raok
 *
 */
public class SubmissionDataFileWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private SubmissionDataFileWidgetModel model;
	final private SubmissionDataFileWidget owner;
	final private SubmitCompleteHandler submissionDataFileCompleteHandler = new SubmissionDataFileCompleteHandler();


	SubmissionDataFileWidgetController(SubmissionDataFileWidgetModel model, SubmissionDataFileWidget owner) {
		this.model = model;
		this.owner = owner;
	}

	void setUpUploadSubmissionDataFile(String submissionLink, String authorEmail) {
		log.enter("setUpUploadSubmissionDataFile");
		
		model.setSubmissionLink(submissionLink);
		model.setAuthor(authorEmail);
		model.setStatus(SubmissionDataFileWidgetModel.STATUS_READY);
		
		log.leave();
	}

	void updateFileName(String fileName) {
		log.enter("updateFileName");
		
		model.setFileName(fileName);
		
		log.leave();
	}

	void updateComment(String comment) {
		log.enter("updateComment");
		
		model.setComment(comment);
		
		log.leave();
	}

	void cancelUploadSubmissionDataFile() {
		log.enter("cancelUploadSubmissionDataFile");
		
		model.setStatus(SubmissionDataFileWidgetModel.STATUS_CANCELLED);
		
		log.leave();
	}

	SubmitCompleteHandler getFormSubmitCompleteHandler() {
		log.enter("getFormSubmitCompleteHandler");
		
		
		log.leave();
		return submissionDataFileCompleteHandler;
	}
	
	//package private for testing purposes
	class SubmissionDataFileCompleteHandler implements SubmitCompleteHandler {

		public void onSubmitComplete(SubmitCompleteEvent arg0) {
			log.enter("SubmissionDataFileCompleteHandler::onSubmitComplete");
			
			log.debug(arg0.getResults());
			
			model.setStatus(SubmissionDataFileWidgetModel.STATUS_UPLOADED);
			
			//alert owner
			owner.fireOnUserActionSubmissionDataFileUploaded(model.getSubmissionLink());
			
			log.leave();
		}
		
	}
	

}
