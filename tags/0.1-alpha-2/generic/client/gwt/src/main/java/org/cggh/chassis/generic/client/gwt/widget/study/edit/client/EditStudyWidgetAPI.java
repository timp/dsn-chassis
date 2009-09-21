package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

public interface EditStudyWidgetAPI {

	public void addEditStudyWidgetListener(EditStudyWidgetPubSubAPI listener);

	public void editStudyEntry(StudyEntry studyEntryToEdit);

}