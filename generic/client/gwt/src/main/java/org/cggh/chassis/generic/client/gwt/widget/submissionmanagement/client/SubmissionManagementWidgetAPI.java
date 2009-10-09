package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import com.google.gwt.user.client.ui.Panel;

public interface SubmissionManagementWidgetAPI {

	public Panel getMenuCanvas();
	
	public void addSubmissionManagementWidgetListener(SubmissionManagementWidgetPubSubAPI listener);
	
	public void resetWidget();

}