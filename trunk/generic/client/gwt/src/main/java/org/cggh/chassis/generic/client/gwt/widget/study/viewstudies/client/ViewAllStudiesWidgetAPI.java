package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.Set;


public interface ViewAllStudiesWidgetAPI {

	public void loadStudies();
	
	public void loadStudies(Set<String> studyEntryURLsToLoad);

	public void addViewAllStudiesWidgetListener(ViewAllStudiesWidgetPubSubAPI listener);

}