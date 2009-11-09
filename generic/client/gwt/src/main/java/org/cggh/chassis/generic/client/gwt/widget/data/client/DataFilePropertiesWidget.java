/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class DataFilePropertiesWidget 
	extends DelegatingWidget<DataFilePropertiesWidgetModel, DataFilePropertiesWidgetRenderer> {

	
	
	
	private Log log;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		log.debug("instantiate model");
		this.model = new DataFilePropertiesWidgetModel(this);

		log.debug("instantiate default renderer");
		this.renderer = new DataFilePropertiesWidgetRenderer();
		
		log.debug("set renderer canvas");
		this.renderer.setCanvas(this.contentBox);

		log.leave();

	}

	
	
	
	
	/**
	 * Set the submission entry to display properties for.
	 * 
	 * @param entry
	 */
	public void setEntry(DataFileEntry entry) {
		log.enter("setEntry");

		this.model.setEntry(entry);

		// should not need to do anything else, renderer will automatically
		// update UI on submission entry change
		
		log.leave();
	}






	private void ensureLog() {
		log = LogFactory.getLog(DataFilePropertiesWidget.class);
	}
	
	
	
	
	
}
