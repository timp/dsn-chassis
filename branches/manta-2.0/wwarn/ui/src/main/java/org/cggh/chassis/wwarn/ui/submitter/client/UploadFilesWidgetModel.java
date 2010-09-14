/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;


/**
 * @author aliman
 *
 */
public class UploadFilesWidgetModel extends AsyncWidgetModel {

	private String errorMessage = "";
	
	public static class RetrieveStudyPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveStudyPendingStatus STATUS_RETRIEVE_STUDY_PENDING = new RetrieveStudyPendingStatus();
	
	public static class StudyNotFoundStatus extends ReadyStatus {}
	public static final StudyNotFoundStatus STATUS_STUDY_NOT_FOUND = new StudyNotFoundStatus();
	
	public static class RetrieveUploadedFilesPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveUploadedFilesPendingStatus STATUS_RETRIEVE_UPLOADED_FILES_PENDING = new RetrieveUploadedFilesPendingStatus();
	
	public static class ReadyForInteractionStatus extends ReadyStatus {}
	public static final ReadyForInteractionStatus STATUS_READY_FOR_INTERACTION = new ReadyForInteractionStatus();
	
	public static class FileUploadPendingStatus extends AsyncRequestPendingStatus {}
	public static final FileUploadPendingStatus STATUS_FILE_UPLOAD_PENDING = new FileUploadPendingStatus();

	public static class FileDeletePendingStatus extends AsyncRequestPendingStatus {}
	public static final FileDeletePendingStatus STATUS_FILE_DELETE_PENDING = new FileDeletePendingStatus();
	
	
	
	
	public final ObservableProperty<String> selectedStudyId = new ObservableProperty<String>();
	public final ObservableProperty<Element> studyEntryElement = new ObservableProperty<Element>();
	public final ObservableProperty<Document> uploadFeedDoc = new ObservableProperty<Document>();
	

	
	public UploadFilesWidgetModel() {
		selectedStudyId.set(null);
		studyEntryElement.set(null);
		uploadFeedDoc.set(null);
	}



	public void setErrorMessage(String message) {
		this.errorMessage  = message;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	




}
