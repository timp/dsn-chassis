/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class MyDatasetsWidget 
	extends DelegatingWidget<MyDatasetsWidgetModel, MyDatasetsWidgetRenderer> {
	private Log log = LogFactory.getLog(MyDatasetsWidget.class);


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new MyDatasetsWidgetModel();
		this.renderer = new MyDatasetsWidgetRenderer();
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
