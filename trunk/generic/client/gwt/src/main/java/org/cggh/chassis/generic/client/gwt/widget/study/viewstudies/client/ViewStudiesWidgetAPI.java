package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.Set;


public interface ViewStudiesWidgetAPI {

	public void loadStudies();
	
	public void loadStudies(Set<String> studyEntryURLsToLoad);

	public void addViewAllStudiesWidgetListener(ViewStudiesWidgetPubSubAPI listener);
	
	public void loadStudiesByAuthorEmail(String authorEmail);

}