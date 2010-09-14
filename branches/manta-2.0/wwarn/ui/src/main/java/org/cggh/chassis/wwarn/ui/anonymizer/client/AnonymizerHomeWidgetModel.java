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
public class AnonymizerHomeWidgetModel extends AsyncWidgetModel {

	
	public final ObservableProperty<Document> submissionFeed = new ObservableProperty<Document>();
	private String errorMessage;
	
	

	public AnonymizerHomeWidgetModel() {
		submissionFeed.set(null);
	}
	
	
	@Override
	public void init() {
		super.init();
	}
	
	
	public static class RetrieveSubmissionsPendingStatus extends AsyncRequestPendingStatus {}
	public static class SubmissionsNotFoundStatus extends ReadyStatus {}
	public static class SubmissionsRetrievedStatus extends ReadyStatus {}
	
	public static final Status STATUS_RETRIEVE_SUBMISSIONS_PENDING = new RetrieveSubmissionsPendingStatus();
	public static final Status STATUS_SUBMISSIONS_NOT_FOUND = new SubmissionsNotFoundStatus();
	public static final Status STATUS_SUBMISSIONS_RETRIEVED = new SubmissionsRetrievedStatus();


	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}



}
