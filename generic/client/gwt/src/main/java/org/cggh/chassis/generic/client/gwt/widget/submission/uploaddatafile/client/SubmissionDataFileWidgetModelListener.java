/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

/**
 * @author raok
 *
 */
public interface SubmissionDataFileWidgetModelListener {

	public void onCommentChanged(String before, String after, Boolean isValid);

	public void onStatusChanged(Integer before, Integer after);

	public void onFileNameChanged(String before, String after, Boolean isValid);

	public void onSubmissionLinkChanged(String before, String after);

	public void onAuthorChanged(String before, String after);

	public void onSubmittedDataFileFormChanged(Boolean isValid);
	
}
