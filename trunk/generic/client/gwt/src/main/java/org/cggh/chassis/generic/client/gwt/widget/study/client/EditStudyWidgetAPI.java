package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


public interface EditStudyWidgetAPI {

	public void addListener(EditStudyWidgetPubSubAPI listener);

	public void editStudyEntry(StudyEntry studyEntryToEdit);

}