package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

public interface CreateStudyWidgetAPI {

	public void setUpNewStudy(String authorEmail);

	public void addListener(CreateStudyWidgetPubSubAPI listener);

}