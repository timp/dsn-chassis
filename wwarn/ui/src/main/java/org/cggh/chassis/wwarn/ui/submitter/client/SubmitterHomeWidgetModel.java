/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.xml.client.Document;


/**
 * @author lee
 *
 */
public class SubmitterHomeWidgetModel extends AsyncWidgetModel {


	private Log log = LogFactory.getLog(SubmitterHomeWidgetModel.class);
	
	
//	private Document submissionFeed;
	public final ObservableProperty<Document> submissionFeed = new ObservableProperty<Document>();
	
	
	public SubmitterHomeWidgetModel() {
		submissionFeed.set(null);
	}
	
	
	@Override
	public void init() {
		super.init();
	}
	
//	public void setSubmissions(Document submissionsFeedDoc) {
//		
//		log.enter("setSubmissions");
//
//		SubmissionsChangeEvent e = new SubmissionsChangeEvent(this.submissionFeed, submissionsFeedDoc);
//		
//		this.submissionFeed = submissionsFeedDoc;
//		
//		this.fireChangeEvent(e);
//		
//		log.leave();
//	}

//	public Document getSubmissions() {
//		return this.submissionFeed;
//	}

//	public static class SubmissionsChangeEvent extends ModelChangeEvent<Document, SubmissionsChangeHandler> {
//
//		public static final Type<SubmissionsChangeHandler> TYPE = new Type<SubmissionsChangeHandler>();
//		
//		public SubmissionsChangeEvent(Document before, Document after) {
//			super(before, after);		
//		}
//
//		@Override
//		protected void dispatch(SubmissionsChangeHandler h) {
//			h.onChange(this);
//		}
//		
//		@Override
//		public com.google.gwt.event.shared.GwtEvent.Type<SubmissionsChangeHandler> getAssociatedType() {
//			return TYPE;
//		}
//
//	}
	
//	public interface SubmissionsChangeHandler extends ModelChangeHandler {
//
//		public void onChange(SubmissionsChangeEvent submissionsChangeEvent);
//		
//
//	}	
	
//	public HandlerRegistration addSubmissionsChangeHandler(SubmissionsChangeHandler h) {
//		return this.addChangeHandler(h, SubmissionsChangeEvent.TYPE);
//	}


	
	public static class RetrieveSubmissionsPendingStatus extends AsyncRequestPendingStatus {}
	public static class SubmissionsNotFoundStatus extends ReadyStatus {}
	public static class SubmissionsRetrievedStatus extends ReadyStatus {}
	
	public static final Status STATUS_RETRIEVE_SUBMISSIONS_PENDING = new RetrieveSubmissionsPendingStatus();
	public static final Status STATUS_SUBMISSIONS_NOT_FOUND = new SubmissionsNotFoundStatus();
	public static final Status STATUS_SUBMISSIONS_RETRIEVED = new SubmissionsRetrievedStatus();
	


}
