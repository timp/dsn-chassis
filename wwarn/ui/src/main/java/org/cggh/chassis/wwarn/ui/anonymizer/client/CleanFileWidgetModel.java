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
public class CleanFileWidgetModel extends AsyncWidgetModel {

	
	private String errorMessage;
	
	public final ObservableProperty<Element> fileToBeCleanedEntryElement = new ObservableProperty<Element>();
	
	
	// Needed to re-establish from memory after browser refresh, for example. 
	public final ObservableProperty<String> fileToBeCleanedID = new ObservableProperty<String>();
	
	

	public CleanFileWidgetModel() {

		fileToBeCleanedEntryElement.set(null);
		fileToBeCleanedID.set(null);
		
	}

	public static class RetrieveFileToBeCleanedEntryElementPendingStatus extends AsyncRequestPendingStatus {}
	public static class FileToBeCleanedEntryElementNotFoundStatus extends ReadyStatus {}
	public static class FileToBeCleanedEntryElementRetrievedStatus extends ReadyStatus {}
	
	public static final Status STATUS_RETRIEVE_FILE_TO_BE_CLEANED_PENDING = new RetrieveFileToBeCleanedEntryElementPendingStatus();
	public static final Status STATUS_FILE_TO_BE_CLEANED_NOT_FOUND = new FileToBeCleanedEntryElementNotFoundStatus();
	public static final Status STATUS_FILE_TO_BE_CLEANED_RETRIEVED = new FileToBeCleanedEntryElementRetrievedStatus();

	
	public static class CleanFileFormSubmissionPendingStatus extends AsyncRequestPendingStatus {}
	public static final CleanFileFormSubmissionPendingStatus STATUS_CLEAN_FILE_FORM_SUBMISSION_PENDING = new CleanFileFormSubmissionPendingStatus();
	
	public static class CleanFileFormSubmissionMalformedResultsStatus extends AsyncRequestPendingStatus {}
	public static final CleanFileFormSubmissionMalformedResultsStatus STATUS_CLEAN_FILE_FORM_SUBMISSION_MALFORMED_RESULTS = new CleanFileFormSubmissionMalformedResultsStatus();
		
	
	public void setFileToBeCleanedEntryElement(Element fileToBeCleanedEntryElement) {
		
		this.fileToBeCleanedEntryElement.set(fileToBeCleanedEntryElement);

	}	

	
	public Element getFileToBeCleanedEntryElement() {
		return this.fileToBeCleanedEntryElement.get();
	}	
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}



	public void setFileToBeCleanedID(String fileToBeCleanedID) {

		this.fileToBeCleanedID.set(fileToBeCleanedID);
		
	}


}
