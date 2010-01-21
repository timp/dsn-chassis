/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AddInformationWidgetModel extends AsyncWidgetModel {

	public static class RetrieveSubmissionPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveSubmissionPendingStatus STATUS_RETRIEVE_SUBMISSION_PENDING = new RetrieveSubmissionPendingStatus();
	
	public static class RetrieveQuestionnairePendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveQuestionnairePendingStatus STATUS_RETRIEVE_QUESTIONNAIRE_PENDING = new RetrieveQuestionnairePendingStatus();
	
	public static class SaveStudyPendingStatus extends AsyncRequestPendingStatus {}
	public static final SaveStudyPendingStatus STATUS_SAVE_STUDY_PENDING = new SaveStudyPendingStatus();
	
	public static class SubmissionNotFoundStatus extends ReadyStatus {}
	public static final SubmissionNotFoundStatus STATUS_SUBMISSION_NOT_FOUND = new SubmissionNotFoundStatus();
	
	public static class ReadyForInteractionStatus extends ReadyStatus {}
	public static final ReadyForInteractionStatus STATUS_READY_FOR_INTERACTION = new ReadyForInteractionStatus();
	
	
	
	
	private String submissionId;
	private Element submissionEntryElement;
	private Element studyEntryElement;


	
	
	public String getSubmissionId() {
		return submissionId;
	}
	
	
	
	public void setSubmissionId(String submissionId) {
		// don't fire a change event, no-one is interested
		this.submissionId = submissionId;
	}
	
	
	
	public Element getSubmissionEntryElement() {
		return submissionEntryElement;
	}
	
	
	
	public void setSubmissionEntryElement(Element submissionEntryElement) {
		SubmissionEntryChangeEvent e = new SubmissionEntryChangeEvent(this.submissionEntryElement, submissionEntryElement);
		this.submissionEntryElement = submissionEntryElement;
		this.fireChangeEvent(e);
	}




	public Element getStudyEntryElement() {
		return studyEntryElement;
	}
	
	
	
	public void setStudyEntryElement(Element studyEntryElement) {
		StudyEntryChangeEvent e = new StudyEntryChangeEvent(this.studyEntryElement, studyEntryElement);
		this.studyEntryElement = studyEntryElement;
		this.fireChangeEvent(e);
	}




	public HandlerRegistration addStudyEntryChangeHandler(StudyEntryChangeHandler h) {
		return this.addChangeHandler(h, StudyEntryChangeEvent.TYPE);
	}
	
	
	
	
	public static class StudyEntryChangeEvent extends ModelChangeEvent<Element, StudyEntryChangeHandler> {

		public static final Type<StudyEntryChangeHandler> TYPE = new Type<StudyEntryChangeHandler>();
		
		public StudyEntryChangeEvent(Element before, Element after) {
			super(before, after);
		}

		@Override
		protected void dispatch(StudyEntryChangeHandler h) {
			h.onChange(this);
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<StudyEntryChangeHandler> getAssociatedType() {
			return TYPE;
		}
		
	}
	
	
	
	public interface StudyEntryChangeHandler extends ModelChangeHandler {
		
		public void onChange(StudyEntryChangeEvent e);
		
	}
	
	
	
	
	public HandlerRegistration addSubmissionEntryChangeHandler(SubmissionEntryChangeHandler h) {
		return this.addChangeHandler(h, SubmissionEntryChangeEvent.TYPE);
	}
	
	
	
	
	public static class SubmissionEntryChangeEvent extends ModelChangeEvent<Element, SubmissionEntryChangeHandler> {

		public static final Type<SubmissionEntryChangeHandler> TYPE = new Type<SubmissionEntryChangeHandler>();
		
		public SubmissionEntryChangeEvent(Element before, Element after) {
			super(before, after);
		}

		@Override
		protected void dispatch(SubmissionEntryChangeHandler h) {
			h.onChange(this);
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<SubmissionEntryChangeHandler> getAssociatedType() {
			return TYPE;
		}
		
	}
	
	
	
	public interface SubmissionEntryChangeHandler extends ModelChangeHandler {
		
		public void onChange(SubmissionEntryChangeEvent e);
		
	}
	
}
