/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class ViewDataFileWidget 
	extends DelegatingWidget<ViewDataFileWidgetModel, ViewDataFileWidgetRenderer> {
	private Log log = LogFactory.getLog(ViewDataFileWidget.class);
	private ViewDataFileWidgetController controller;


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new ViewDataFileWidgetModel(this);
		this.controller = new ViewDataFileWidgetController(this, this.model);
		this.renderer = new ViewDataFileWidgetRenderer();
		this.renderer.setCanvas(this.contentBox);

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
	public void getEntry(String id) {
		log.enter("setEntry");

		// delegate to controller
		this.controller.getEntry(id);
		
		log.leave();
	}






}
