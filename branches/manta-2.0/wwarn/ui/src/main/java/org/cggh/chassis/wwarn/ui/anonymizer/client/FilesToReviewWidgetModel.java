/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;


/**
 * @author lee
 *
 */
public class FilesToReviewWidgetModel extends AsyncWidgetModel {

	
	public final ObservableProperty<Document> filesToReviewFeedDoc = new ObservableProperty<Document>();
	
	public final ObservableProperty<Element> fileToBeReviewedEntryElement = new ObservableProperty<Element>();
	
	private String errorMessage;

	public FilesToReviewWidgetModel() {
		filesToReviewFeedDoc.set(null);
		fileToBeReviewedEntryElement.set(null);
	}

	@Override
	public void init() {
		super.init();
	}	
	
	public static class RetrieveFilesToReviewPendingStatus extends AsyncRequestPendingStatus {}
	public static class FilesToReviewNotFoundStatus extends ReadyStatus {}
	public static class FilesToReviewRetrievedStatus extends ReadyStatus {}
	
	public static final Status STATUS_RETRIEVE_FILES_TO_REVIEW_PENDING = new RetrieveFilesToReviewPendingStatus();
	public static final Status STATUS_FILES_TO_REVIEW_NOT_FOUND = new FilesToReviewNotFoundStatus();
	public static final Status STATUS_FILES_TO_REVIEW_RETRIEVED = new FilesToReviewRetrievedStatus();	
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setFileToBeReviewedEntryElement(Element fileToBeReviewedEntryElement) {
		
		this.fileToBeReviewedEntryElement.set(fileToBeReviewedEntryElement);
		
	}
	
	public Element getFileToBeReviewedEntryElement() {
		
		return this.fileToBeReviewedEntryElement.get();

	}

}
