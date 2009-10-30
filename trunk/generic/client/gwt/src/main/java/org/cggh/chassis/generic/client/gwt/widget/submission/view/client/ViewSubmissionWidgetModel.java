/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.SubmissionPropertiesWidgetModel.SubmissionEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.SubmissionPropertiesWidgetModel.SubmissionEntryChangeEvent;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;


/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetModel extends ChassisWidgetModel {

	
	
	
	private Log log;

	
	
	
	// model properties
	private int status;
	private SubmissionEntry entry;
	
	
	
	
	// property value constants
	public static final int STATUS_ERROR = -1;
	public static final int STATUS_INITIAL = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_RETRIEVE_PENDING = 2;
	
	
	
	
	/**
	 * @param owner
	 */
	public ViewSubmissionWidgetModel(ViewSubmissionWidget owner) {
		super(owner);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetModel#init()
	 */
	@Override
	protected void init() {
		log = LogFactory.getLog(ViewSubmissionWidgetModel.class); // need to instantiate here because called from superclass constructor
		log.enter("init");
		
		// initialise model properties
		this.status = STATUS_INITIAL;
		this.entry = null;
		
		log.leave();
		
	}




	public void setStatus(int status) {
		StatusChangeEvent e = new StatusChangeEvent(this.status, status);
		this.status = status;
		this.fireChangeEvent(e);
	}




	public int getStatus() {
		return status;
	}




	public void setSubmissionEntry(SubmissionEntry entry) {
		SubmissionEntryChangeEvent e = new SubmissionEntryChangeEvent(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}




	public SubmissionEntry getSubmissionEntry() {
		return entry;
	}

	
	
	
	public interface StatusChangeHandler extends ModelChangeHandler {
		
		public void onStatusChanged(StatusChangeEvent e);

	}
	
	
	
	
	public interface SubmissionEntryChangeHandler extends ModelChangeHandler {
		
		public void onSubmissionEntryChanged(SubmissionEntryChangeEvent e);

	}
	
	
	
	
	public static class StatusChangeEvent extends ModelChangeEvent<Integer, StatusChangeHandler> {

		public static final Type<StatusChangeHandler> TYPE = new Type<StatusChangeHandler>();
			
		public StatusChangeEvent(Integer before, Integer after) { super(before, after); }

		@Override
		protected void dispatch(StatusChangeHandler handler) { handler.onStatusChanged(this); }

		@Override
		public Type<StatusChangeHandler> getAssociatedType() { return TYPE; }
		
	}
	
	
	
	

	public static class SubmissionEntryChangeEvent extends ModelChangeEvent<SubmissionEntry, SubmissionEntryChangeHandler> {

		public static final Type<SubmissionEntryChangeHandler> TYPE = new Type<SubmissionEntryChangeHandler>();
			
		public SubmissionEntryChangeEvent(SubmissionEntry before, SubmissionEntry after) { super(before, after); }

		@Override
		protected void dispatch(SubmissionEntryChangeHandler handler) { handler.onSubmissionEntryChanged(this); }

		@Override
		public Type<SubmissionEntryChangeHandler> getAssociatedType() { return TYPE; }
		
	}
	
	
	
	
	public HandlerRegistration addStatusChangeHandler(StatusChangeHandler h) {
		return this.addChangeHandler(h, StatusChangeEvent.TYPE);
	}



	
	public HandlerRegistration addSubmissionEntryChangeHandler(SubmissionEntryChangeHandler h) {
		return this.addChangeHandler(h, SubmissionEntryChangeEvent.TYPE);
	}
	
	
	
	
}
