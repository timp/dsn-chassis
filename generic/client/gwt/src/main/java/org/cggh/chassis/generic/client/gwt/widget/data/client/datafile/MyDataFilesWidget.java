/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class MyDataFilesWidget 
	extends DelegatingWidget<MyDataFilesWidgetModel, MyDataFilesWidgetRenderer> {
	
	
	
	
	private Log log;
	private MyDataFilesWidgetController controller;

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new MyDataFilesWidgetModel(this);
		this.controller = new MyDataFilesWidgetController(this, this.model);
		this.renderer = new MyDataFilesWidgetRenderer(this);
		this.renderer.setCanvas(this.contentBox);

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(MyDataFilesWidget.class);
	}




	/**
	 * 
	 */
	public void refreshDataFiles() {
		log.enter("refreshDataFiles");
		
		// delegate to controller
		this.controller.refreshDataFiles();
		
		log.leave();
	}




	public HandlerRegistration addViewDataFileActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, ViewDataFileActionEvent.TYPE);
	}


}
