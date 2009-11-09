/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class DataFilePropertiesWidgetModel extends ChassisWidgetModel {

	
	
	
	private Log log;

	
	
	private DataFileEntry entry;
	
	
	
	
	public DataFilePropertiesWidgetModel(DataFilePropertiesWidget owner) {
		super(owner);
	}
	
	

	
	
	/**
	 * Set up initial state of model.
	 * 
	 * @see org.cggh.chassis.generic.widget.client.WidgetModel#init()
	 */
	@Override
	protected void init() {
		// TODO ensurelog
		log = LogFactory.getLog(DataFilePropertiesWidgetModel.class); // need to instantiate log here because init() is called from superclass constructor
		log.enter("init");
		
		this.entry = null;
		
		log.leave();
		
	}
	
	
	
	
	
	/**
	 * @return the entry
	 */
	public DataFileEntry getEntry() {
		return entry;
	}



	
	/**
	 * @param entry the entry to set
	 */
	public void setEntry(DataFileEntry entry) {
		DataFileEntryChangeEvent e = new DataFileEntryChangeEvent(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}



	

	public HandlerRegistration addDataFileEntryChangeHandler(DataFileEntryChangeHandler h) {
		return this.addChangeHandler(h, DataFileEntryChangeEvent.TYPE);
	}
	
	
	
	
}
