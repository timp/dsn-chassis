/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.client.gwt.common.client.widget.AtomEntryPropertiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class DataFilePropertiesWidget 
	extends AtomEntryPropertiesWidget<DataFileEntry> {

	
	
	
	private Log log;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // call before we do anything here
		
		// nothing else to do

		log.leave();
	}

	




	private void ensureLog() {
		log = LogFactory.getLog(DataFilePropertiesWidget.class);
	}
	
	
	
	
	
}
