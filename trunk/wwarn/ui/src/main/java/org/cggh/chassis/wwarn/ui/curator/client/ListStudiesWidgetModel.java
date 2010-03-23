

package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.AsyncRequestPendingStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ReadyStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author timp
 */
public class ListStudiesWidgetModel {

	public static class RetrieveStudyFeedPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveStudyFeedPendingStatus STATUS_RETRIEVE_STUDY_FEED_PENDING = new RetrieveStudyFeedPendingStatus();

	public static class ReadyForInteractionStatus extends ReadyStatus {}
	public static final ReadyForInteractionStatus STATUS_READY_FOR_INTERACTION = new ReadyForInteractionStatus();	
	
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	public final ObservableProperty<Document> studyFeed = new ObservableProperty<Document>();
	public final ObservableProperty<Element> studyFeedElement = new ObservableProperty<Element>(); 
	
	
}
