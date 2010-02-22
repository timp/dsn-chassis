package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import com.google.gwt.xml.client.Document;

/**
 * @author timp
 * @since 12 Jan 2010
 *
 */
public class SelectStudyWidgetModel extends AsyncWidgetModel {
	
	public static class RetrieveFeedPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveFeedPendingStatus STATUS_RETRIEVE_STUDIES_PENDING = new RetrieveFeedPendingStatus();
	
	public static class StudiesRetrievedStatus extends ReadyStatus {}
	public static final StudiesRetrievedStatus STATUS_STUDIES_RETRIEVED = new StudiesRetrievedStatus();

	public static class CreateEntryPendingStatus extends AsyncRequestPendingStatus {}
	public static final CreateEntryPendingStatus STATUS_CREATE_STUDY_PENDING = new CreateEntryPendingStatus();
	
	public static class StudyCreatedStatus extends ReadyStatus {}
	public static final StudyCreatedStatus STATUS_STUDY_CREATED = new StudyCreatedStatus();

	public final ObservableProperty<Document> studyFeed = new ObservableProperty<Document>();
	
	public final ObservableProperty<String> selectedStudyId  = new ObservableProperty<String>();

	private String message;
	
	public Document getStudyFeedDoc() {
		return studyFeed.get();
	}

	/** return nullable id to next widget */
	public String getSelectedStudyId() {
		return selectedStudyId.get();
	}

	public void setStudyFeed(Document in) {
		this.studyFeed.set(in);
	}
	
	public Integer getStudyCount() { 
		Integer count = null;
		if (studyFeed == null) 
			return count;
		else 
			return AtomHelper.getEntries(studyFeed.get().getDocumentElement()).size();
	}

	public void setSelectedStudy(String selectedStudyIdIn) {
		if (selectedStudyIdIn != null && selectedStudyIdIn.equals(""))
			selectedStudyId.set(null);
		else
			selectedStudyId.set(selectedStudyIdIn);
	}

	public boolean isSelectedStudyIdNotNull() {
		if (selectedStudyId.get() != null)
			return true;
		return false;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
