/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class ViewDataFileWidget extends DelegatingWidget {
	private Log log = LogFactory.getLog(ViewDataFileWidget.class);


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		// TODO

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataManagementWidget.class);
	}




	/**
	 * @param dataFileEntry
	 */
	public void setModel(DataFileEntry dataFileEntry) {
		log.enter("setModel");
		
		// TODO Auto-generated method stub
		
		log.leave();
		
	}






}
