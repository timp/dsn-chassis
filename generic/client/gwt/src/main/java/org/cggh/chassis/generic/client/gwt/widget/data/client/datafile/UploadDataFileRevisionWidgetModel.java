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
public class UploadDataFileRevisionWidgetModel extends AsyncWidgetModel {
	
	
	
	
	
	private Log log;

	
	
	private DataFileEntry entry;
	
	
	
	
	
	@Override
	public void init() {
		ensureLog();
		super.init();
		this.setEntry(null);
	}
	
	
	
	
	
	private void ensureLog() {
		if (log == null) log  = LogFactory.getLog(UploadDataFileRevisionWidgetModel.class);
	}

	
	
	
	public HandlerRegistration addDataFileEntryChangeHandler(DataFileEntryChangeHandler h) {
		return this.addChangeHandler(h, DataFileEntryChangeEvent.TYPE);
	}





	public void setEntry(DataFileEntry entry) {
		DataFileEntryChangeEvent e = new DataFileEntryChangeEvent(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}





	public DataFileEntry getEntry() {
		return entry;
	}

}
