package org.cggh.chassis.generic.client.gwt.widget.study.client;

public interface NewStudyWidgetAPI {

	public void setUpNewStudy(String authorEmail);

	public void addListener(NewStudyWidgetPubSubAPI listener);

}