/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import com.google.gwt.xml.client.Document;


/**
 * @author lee
 *
 */
public class FilesToCleanWidgetModel extends AsyncWidgetModel {

	
	public final ObservableProperty<Document> filesToCleanFeedDoc = new ObservableProperty<Document>();
	private String errorMessage;
	
	

	public FilesToCleanWidgetModel() {
		filesToCleanFeedDoc.set(null);
	}
	
	
	@Override
	public void init() {
		super.init();
	}
	
	
	public static class RetrieveFilesToCleanPendingStatus extends AsyncRequestPendingStatus {}
	public static class FilesToCleanNotFoundStatus extends ReadyStatus {}
	public static class FilesToCleanRetrievedStatus extends ReadyStatus {}
	
	public static final Status STATUS_RETRIEVE_FILES_TO_CLEAN_PENDING = new RetrieveFilesToCleanPendingStatus();
	public static final Status STATUS_FILES_TO_CLEAN_NOT_FOUND = new FilesToCleanNotFoundStatus();
	public static final Status STATUS_FILES_TO_CLEAN_RETRIEVED = new FilesToCleanRetrievedStatus();	

	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}



}
