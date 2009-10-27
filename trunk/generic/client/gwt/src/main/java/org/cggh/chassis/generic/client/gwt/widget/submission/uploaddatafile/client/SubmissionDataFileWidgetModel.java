/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import java.util.HashSet;
import java.util.Set;


/**
 * @author raok
 *
 */
public class SubmissionDataFileWidgetModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_READY = 1;
	public static final Integer STATUS_UPLOADING = 2;
	public static final Integer STATUS_UPLOADED = 3;
	public static final Integer STATUS_ERROR = 4;
	public static final Integer STATUS_CANCELLED = 5;
	
	private Integer status = STATUS_INITIAL;
	private String fileName;
	private String comment;
	private String submissionLink;
	private String author;
	
	private Set<SubmissionDataFileWidgetModelListener> listeners = new HashSet<SubmissionDataFileWidgetModelListener>();
	
	
	public String getFileName() {
		return fileName;
	}
	
	
	public void setFileName(String fileName) {
		
		String before = this.fileName;
		
		this.fileName = fileName;
		
		fireOnFileNameChanged(before);
		fireOnEntryChanged();
	}
	
	
	private void fireOnEntryChanged() {
		
		// unable to implement validation on fileName with creating a fileUpload widget that fires events.
		// So do not check for filename validation here.
		
		Boolean isEntryValid = isCommentValid();
		
		for (SubmissionDataFileWidgetModelListener listener : listeners) {
			listener.onSubmittedDataFileFormChanged(isEntryValid);
		}
		
	}


	private void fireOnFileNameChanged(String before) {

		for (SubmissionDataFileWidgetModelListener listener : listeners) {
			listener.onFileNameChanged(before, getFileName(), isFileNameValid());
		}
		
	}


	private Boolean isFileNameValid() {
		//TODO improve validation
		return ((getFileName() != null) && !(getFileName().length() == 0));
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		
		String before = this.comment;
		
		this.comment = comment;
		
		fireOnCommentChanged(before);
		fireOnEntryChanged();
	}
	
	
	private void fireOnCommentChanged(String before) {

		for (SubmissionDataFileWidgetModelListener listener : listeners) {
			listener.onCommentChanged(before, getComment(), isCommentValid());
		}
	}


	private Boolean isCommentValid() {
		//TODO improve validation
		return ((getComment() != null) && !(getComment().length() == 0));
	}


	public Integer getStatus() {
		return status ;
	}
	
	
	public void setStatus(Integer status) {
		
		Integer before = this.status;
		
		this.status = status;
		
		fireOnStatusChanged(before);
	}
	
	
	private void fireOnStatusChanged(Integer before) {

		for (SubmissionDataFileWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, getStatus());
		}
	}


	public void addListener(SubmissionDataFileWidgetModelListener listener) {
		listeners.add(listener);
	}


	public String getSubmissionLink() {
		return submissionLink;
	}


	public void setSubmissionLink(String submissionLink) {
		
		String before = this.submissionLink;
		
		this.submissionLink = submissionLink;
		
		fireOnSubmissionLinkChanged(before);
	}


	private void fireOnSubmissionLinkChanged(String before) {

		for (SubmissionDataFileWidgetModelListener listener : listeners) {
			listener.onSubmissionLinkChanged(before, getSubmissionLink());
		}
	}


	public void setAuthor(String author) {

		String before = this.author;
		
		this.author = author;
		
		fireOnAuthorChanged(before);
	}


	private void fireOnAuthorChanged(String before) {

		for (SubmissionDataFileWidgetModelListener listener : listeners) {
			listener.onAuthorChanged(before, getAuthor());
		}
	}


	public String getAuthor() {
		return author;
	}

}
