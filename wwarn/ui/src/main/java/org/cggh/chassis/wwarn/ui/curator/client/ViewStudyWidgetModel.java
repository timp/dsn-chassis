

package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.AsyncRequestPendingStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ReadyStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.xml.client.Element;


public class ViewStudyWidgetModel {

	
	public static class RetrieveQuestionnairePendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveQuestionnairePendingStatus STATUS_RETRIEVE_QUESTIONNAIRE_PENDING = new RetrieveQuestionnairePendingStatus();
	
	public static class RetrieveStudyPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveStudyPendingStatus STATUS_RETRIEVE_STUDY_PENDING = new RetrieveStudyPendingStatus();
	
	public static class StudyEntryElementRetrievedStatus extends ReadyStatus {}
	public static final StudyEntryElementRetrievedStatus STATUS_STUDY_RETRIEVED = new StudyEntryElementRetrievedStatus();	
	
	public static class StudyEntryElementNotFoundStatus extends ReadyStatus {}
	public static final StudyEntryElementNotFoundStatus STATUS_STUDY_NOT_FOUND = new StudyEntryElementNotFoundStatus();		

	public static class SaveStudyPendingStatus extends AsyncRequestPendingStatus {}
	public static final SaveStudyPendingStatus STATUS_SAVE_STUDY_PENDING = new SaveStudyPendingStatus();
	
	public static class ReadyForInteractionStatus extends ReadyStatus {}
	public static final ReadyForInteractionStatus STATUS_READY_FOR_INTERACTION = new ReadyForInteractionStatus();
	
	
	
	
	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();
	public final ObservableProperty<Element> studyEntryElement = new ObservableProperty<Element>();
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	
	// Needed to re-establish from memory after browser refresh, for example. 
	public final ObservableProperty<String> studyID = new ObservableProperty<String>();
		
	
	public void setStudyEntryElement(Element studyEntryElement) {
		
		this.studyEntryElement.set(studyEntryElement);

	}

	public void setStudyID(String studyID) {

		this.studyID.set(studyID);
		
	}
	
}
