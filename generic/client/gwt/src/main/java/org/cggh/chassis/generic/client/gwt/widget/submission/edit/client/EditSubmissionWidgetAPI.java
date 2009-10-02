package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

public interface EditSubmissionWidgetAPI {

	public void addEditSubmissionWidgetListener(EditSubmissionWidgetPubSubAPI listener);

	public void editSubmissionEntry(SubmissionEntry submissionEntryToEdit);

}