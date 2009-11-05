/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;
import org.cggh.chassis.generic.widget.client.ChassisWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidgetModel extends ChassisWidgetModel {

	
	
	
	private Log log;

	
	
	private SubmissionEntry entry;
	
	
	
	
	public SubmissionPropertiesWidgetModel(SubmissionPropertiesWidget owner) {
		super(owner);
	}
	
	

	
	
	/**
	 * Set up initial state of model.
	 * 
	 * @see org.cggh.chassis.generic.widget.client.WidgetModel#init()
	 */
	@Override
	protected void init() {
		log = LogFactory.getLog(SubmissionPropertiesWidgetModel.class); // need to instantiate log here because init() is called from superclass constructor
		log.enter("init");
		
		this.entry = null;
		
		log.leave();
		
	}
	
	
	
	
	
	/**
	 * @return the entry
	 */
	public SubmissionEntry getSubmissionEntry() {
		return entry;
	}



	
	/**
	 * @param entry the entry to set
	 */
	public void setSubmissionEntry(SubmissionEntry entry) {
		SubmissionEntryChangeEvent e = new SubmissionEntryChangeEvent(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}



	

	public HandlerRegistration addSubmissionEntryChangeHandler(SubmissionEntryChangeHandler h) {
		return this.addChangeHandler(h, SubmissionEntryChangeEvent.TYPE);
	}
	
	
	
	
	
	public interface SubmissionEntryChangeHandler extends ModelChangeHandler {
		
		public void onSubmissionEntryChanged(SubmissionEntryChangeEvent e);

	}
	
	
	
	
	
	public static class SubmissionEntryChangeEvent extends ModelChangeEvent<SubmissionEntry, SubmissionEntryChangeHandler> {

		public static final Type<SubmissionEntryChangeHandler> TYPE = new Type<SubmissionEntryChangeHandler>();
			
		public SubmissionEntryChangeEvent(SubmissionEntry before, SubmissionEntry after) { super(before, after); }

		@Override
		protected void dispatch(SubmissionEntryChangeHandler handler) { handler.onSubmissionEntryChanged(this); }

		@Override
		public Type<SubmissionEntryChangeHandler> getAssociatedType() { return TYPE; }
		
	}
	
	
	
	
}
