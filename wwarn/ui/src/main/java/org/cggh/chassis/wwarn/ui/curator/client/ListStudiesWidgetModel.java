

package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.AsyncRequestPendingStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ReadyStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.xml.client.Document;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 */
public class ListStudiesWidgetModel {

	
	public static class RetrieveQuestionnairePendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveQuestionnairePendingStatus STATUS_RETRIEVE_QUESTIONNAIRE_PENDING = new RetrieveQuestionnairePendingStatus();
	
	public static class RetrieveStudyPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveStudyPendingStatus STATUS_RETRIEVE_STUDY_PENDING = new RetrieveStudyPendingStatus();

	public static class SaveStudyPendingStatus extends AsyncRequestPendingStatus {}
	public static final SaveStudyPendingStatus STATUS_SAVE_STUDY_PENDING = new SaveStudyPendingStatus();
	
	public static class ReadyForInteractionStatus extends ReadyStatus {}
	public static final ReadyForInteractionStatus STATUS_READY_FOR_INTERACTION = new ReadyForInteractionStatus();

	public final ObservableProperty<Document> studyFeed = new ObservableProperty<Document>();

	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	
}
