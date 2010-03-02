/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import com.google.gwt.xml.client.Element;


/**
 * @author lee
 *
 */
public class ReviewFileWidgetModel extends AsyncWidgetModel {

	
	private String errorMessage;
	
	public final ObservableProperty<Element> fileToBeReviewedEntryElement = new ObservableProperty<Element>();
	
	
	// Needed to re-establish from memory after browser refresh, for example. 
	public final ObservableProperty<String> fileToBeReviewedID = new ObservableProperty<String>();
	
	

	public ReviewFileWidgetModel() {

		fileToBeReviewedEntryElement.set(null);
		fileToBeReviewedID.set(null);
		
	}

	public static class RetrieveFileToBeReviewedEntryElementPendingStatus extends AsyncRequestPendingStatus {}
	public static class FileToBeReviewedEntryElementNotFoundStatus extends ReadyStatus {}
	public static class FileToBeReviewedEntryElementRetrievedStatus extends ReadyStatus {}
	
	public static final Status STATUS_RETRIEVE_FILE_TO_BE_REVIEWED_PENDING = new RetrieveFileToBeReviewedEntryElementPendingStatus();
	public static final Status STATUS_FILE_TO_BE_REVIEWED_NOT_FOUND = new FileToBeReviewedEntryElementNotFoundStatus();
	public static final Status STATUS_FILE_TO_BE_REVIEWED_RETRIEVED = new FileToBeReviewedEntryElementRetrievedStatus();

	public void setFileToBeReviewedEntryElement(Element fileToBeReviewedEntryElement) {
		
		this.fileToBeReviewedEntryElement.set(fileToBeReviewedEntryElement);

	}	

	
	public Element getFileToBeReviewedEntryElement() {
		return this.fileToBeReviewedEntryElement.get();
	}	
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}



	public void setFileToBeReviewedID(String fileToBeReviewedID) {

		this.fileToBeReviewedID.set(fileToBeReviewedID);
		
	}


}
