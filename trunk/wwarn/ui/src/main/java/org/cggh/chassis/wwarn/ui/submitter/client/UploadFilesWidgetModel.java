/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;


/**
 * @author aliman
 *
 */
public class UploadFilesWidgetModel extends AsyncWidgetModel {

	
	public static class RetrieveStudyPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveStudyPendingStatus STATUS_RETRIEVE_STUDY_PENDING = new RetrieveStudyPendingStatus();
	
	public static class StudyNotFoundStatus extends ReadyStatus {}
	public static final StudyNotFoundStatus STATUS_STUDY_NOT_FOUND = new StudyNotFoundStatus();
	
	public static class RetrieveUploadedFilesPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveUploadedFilesPendingStatus STATUS_RETRIEVE_UPLOADED_FILES_PENDING = new RetrieveUploadedFilesPendingStatus();
	
	public static class ReadyForInteractionStatus extends ReadyStatus {}
	public static final ReadyForInteractionStatus STATUS_READY_FOR_INTERACTION = new ReadyForInteractionStatus();
	
	public static class FileUploadPendingStatus extends AsyncRequestPendingStatus {}
	public static final FileUploadPendingStatus STATUS_FILE_UPLOAD_PENDING = new FileUploadPendingStatus();
	
	
	
	
	private String selectedStudyId;
	private Element studyEntryElement;
	private Document uploadFeedDoc;
	
	
	
	
	@Override
	public void init() {
		super.init();
		this.selectedStudyId = null;
		this.studyEntryElement = null;
		this.uploadFeedDoc = null;
	}




	public void setSelectedStudyId(String selectedStudyId) {
		this.selectedStudyId = selectedStudyId;
	}




	public String getSelectedStudyId() {
		return selectedStudyId;
	}




	public void setStudyEntryElement(Element studyEntryElement) {
		StudyEntryChangeEvent e = new StudyEntryChangeEvent(this.studyEntryElement, studyEntryElement);
		this.studyEntryElement = studyEntryElement;
		this.fireChangeEvent(e);
	}




	public Element getStudyEntryElement() {
		return studyEntryElement;
	}




	public void setUploadFeedDoc(Document uploadFeedDoc) {
		UploadFeedChangeEvent e = new UploadFeedChangeEvent(this.uploadFeedDoc, uploadFeedDoc);
		this.uploadFeedDoc = uploadFeedDoc;
		this.fireChangeEvent(e);
	}




	public Document getUploadFeedDoc() {
		return uploadFeedDoc;
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

	
	
	
	public HandlerRegistration addUploadFeedChangeHandler(UploadFeedChangeHandler h) {
		return this.addChangeHandler(h, UploadFeedChangeEvent.TYPE);
	}
	
	
	
	
	public static class UploadFeedChangeEvent extends ModelChangeEvent<Document, UploadFeedChangeHandler> {

		public static final Type<UploadFeedChangeHandler> TYPE = new Type<UploadFeedChangeHandler>();
		
		public UploadFeedChangeEvent(Document before, Document after) {
			super(before, after);
		}

		@Override
		protected void dispatch(UploadFeedChangeHandler h) {
			h.onChange(this);
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<UploadFeedChangeHandler> getAssociatedType() {
			return TYPE;
		}
		
	}
	
	
	
	public interface UploadFeedChangeHandler extends ModelChangeHandler {
		
		public void onChange(UploadFeedChangeEvent e);
		
	}
	
}
