/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * @author raok
 *
 */
public class UploadSubmissionDataFileWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private UploadSubmissionDataFileWidgetModel model;
	final private UploadSubmissionDataFileWidget owner;
	final private SubmitCompleteHandler submissionDataFileCompleteHandler = new SubmissionDataFileCompleteHandler();


	UploadSubmissionDataFileWidgetController(UploadSubmissionDataFileWidgetModel model, UploadSubmissionDataFileWidget owner) {
		this.model = model;
		this.owner = owner;
	}
	
	public void ready() {
		model.setStatus(UploadSubmissionDataFileWidgetModel.STATUS_READY);
	}

	void cancelUploadSubmissionDataFile() {
		log.enter("cancelUploadSubmissionDataFile");
		
		model.setStatus(UploadSubmissionDataFileWidgetModel.STATUS_CANCELLED);
		
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
			
			model.setStatus(UploadSubmissionDataFileWidgetModel.STATUS_CREATE_SUCCESS);
			
			//alert owner
			owner.fireOnUserActionSubmissionDataFileUploaded();
			
			log.leave();
		}
		
	}
	

}
