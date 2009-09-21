package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

public interface ViewAllStudiesWidgetAPI {

	public void loadStudiesByFeedURL(String feedURL);

	public void addViewAllStudiesWidgetListener(ViewAllStudiesWidgetPubSubAPI listener);

}