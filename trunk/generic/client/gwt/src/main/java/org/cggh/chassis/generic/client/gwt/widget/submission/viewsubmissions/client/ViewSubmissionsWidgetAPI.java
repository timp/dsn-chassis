package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.Set;


public interface ViewSubmissionsWidgetAPI {

	public void loadSubmissions();
	
	public void loadSubmissions(Set<String> submissionEntryURLsToLoad);

	public void addViewAllSubmissionsWidgetListener(ViewSubmissionsWidgetPubSubAPI listener);

}