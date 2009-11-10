/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class MyDataFilesWidget 
	extends DelegatingWidget<MyDataFilesWidgetModel, MyDataFilesWidgetRenderer> {
	private Log log = LogFactory.getLog(MyDataFilesWidget.class);


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new MyDataFilesWidgetModel();
		this.renderer = new MyDataFilesWidgetRenderer();
		this.renderer.setCanvas(this.contentBox);

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataManagementWidget.class);
	}






}
