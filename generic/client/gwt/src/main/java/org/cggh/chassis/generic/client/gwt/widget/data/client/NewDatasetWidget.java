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
public class NewDatasetWidget 
	extends DelegatingWidget<NewDatasetWidgetModel, NewDatasetWidgetRenderer> {

	
	
	

	
	
	
	private Log log = LogFactory.getLog(NewDatasetWidget.class);


	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = new NewDatasetWidgetModel();
		this.renderer = new NewDatasetWidgetRenderer();
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
