package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

public interface ViewStudyWidgetAPI {

	public void loadStudyEntry(StudyEntry studyEntry);

	public void loadStudyByEntryURL(String entryURL);

	public void addViewStudyWidgetListener(ViewStudyWidgetPubSubAPI listener);

}