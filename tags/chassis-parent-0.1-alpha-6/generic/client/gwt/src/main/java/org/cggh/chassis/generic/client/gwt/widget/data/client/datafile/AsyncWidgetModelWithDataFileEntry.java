/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class AsyncWidgetModelWithDataFileEntry extends AsyncWidgetModel {

	
	
	
	
	private Log log;
	
	
	
	
	
	// state variables
	private DataFileEntry entry;

	
	
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
		log = LogFactory.getLog(AsyncWidgetModelWithDataFileEntry.class); // instantiate here because called from superclass constructor
	}




	public void setEntry(DataFileEntry entry) {
		DataFileEntryChangeEvent e = new DataFileEntryChangeEvent(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}




	public DataFileEntry getEntry() {
		return entry;
	}





	public HandlerRegistration addDataFileEntryChangeHandler(DataFileEntryChangeHandler h) {
		return this.addChangeHandler(h, DataFileEntryChangeEvent.TYPE);
	}
	
	


}
