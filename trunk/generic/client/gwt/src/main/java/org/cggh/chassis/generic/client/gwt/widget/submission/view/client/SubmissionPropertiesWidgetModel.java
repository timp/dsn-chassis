/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChangeEvent;
import org.cggh.chassis.generic.widget.client.WidgetModel;

/**
 * @author aliman
 *
 */
public class SubmissionPropertiesWidgetModel extends WidgetModel<SubmissionPropertiesWidgetModelListener> {

	
	
	
	private Log log;

	
	
	private SubmissionEntry entry;
	
	
	
	
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
		ChangeEvent<SubmissionEntry> e = new ChangeEvent<SubmissionEntry>(this.entry, entry);
		this.entry = entry;
		this.fireOnSubmissionEntryChanged(e);
	}



	
	/**
	 * @param e
	 */
	private void fireOnSubmissionEntryChanged(ChangeEvent<SubmissionEntry> e) {
		log.enter("fireOnSubmissionEntryChanged");
		
		for (SubmissionPropertiesWidgetModelListener l : listeners) {
			l.onSubmissionEntryChanged(e);
		}
		
		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.WidgetModel#init()
	 */
	@Override
	protected void init() {
		log = LogFactory.getLog(SubmissionPropertiesWidgetModel.class); // need to instantiate here because init() is called from superclass constructor
		log.enter("init");
		
		this.entry = null;
		
		log.leave();
		
	}
	
	
	
	
}
