/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;


/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetModel extends AsyncWidgetModel {

	
	
	
	private Log log;

	
	
	
	// model properties
	private SubmissionEntry entry;
	
	
	
	
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
		
		super.init();

		// initialise model properties
		this.entry = null;
		
		log.leave();
		
	}



	
	public void setSubmissionEntry(SubmissionEntry entry) {
		SubmissionEntryChangeEvent e = new SubmissionEntryChangeEvent(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}




	public SubmissionEntry getSubmissionEntry() {
		return entry;
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
	
	
	


	
	public HandlerRegistration addSubmissionEntryChangeHandler(SubmissionEntryChangeHandler h) {
		return this.addChangeHandler(h, SubmissionEntryChangeEvent.TYPE);
	}
	
	
	
	
}
