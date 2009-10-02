package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

public interface ViewSubmissionWidgetAPI {

	public void loadSubmissionEntry(SubmissionEntry submissionEntry);

	public void loadSubmissionByEntryURL(String entryURL);

	public void addViewSubmissionWidgetListener(ViewSubmissionWidgetPubSubAPI listener);

}