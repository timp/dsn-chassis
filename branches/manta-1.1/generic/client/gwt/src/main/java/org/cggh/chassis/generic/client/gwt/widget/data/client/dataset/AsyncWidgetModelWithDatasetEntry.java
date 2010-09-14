/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class AsyncWidgetModelWithDatasetEntry extends AsyncWidgetModel {

	
	
	
	// TODO consider push up to new superclass
	
	
	
	
	private Log log;
	
	
	
	
	
	// state variables
	private DatasetEntry entry;

	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetModel#init()
	 */
	@Override
	protected void init() {
		ensureLog();
		log.enter("init");

		super.init();
		setEntry(null);

		log.leave();
	}
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		log = LogFactory.getLog(AsyncWidgetModelWithDatasetEntry.class); // instantiate here because called from superclass constructor
	}




	public void setEntry(DatasetEntry entry) {
		DatasetEntryChangeEvent e = new DatasetEntryChangeEvent(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}




	public DatasetEntry getEntry() {
		return entry;
	}





	public HandlerRegistration addDatasetEntryChangeHandler(DatasetEntryChangeHandler h) {
		return this.addChangeHandler(h, DatasetEntryChangeEvent.TYPE);
	}
	
	


}
