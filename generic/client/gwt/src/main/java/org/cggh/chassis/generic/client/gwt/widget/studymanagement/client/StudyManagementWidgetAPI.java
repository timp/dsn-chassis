package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import com.google.gwt.user.client.ui.Panel;

public interface StudyManagementWidgetAPI {

	public Panel getMenuCanvas();
	
	public void addListener(StudyManagementWidgetPubSubAPI listener);
	
	public void resetWidget();
	
}