/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class ViewDatasetWidget 
	extends DelegatingWidget<ViewDatasetWidgetModel, ViewDatasetWidgetRenderer> {
	private Log log = LogFactory.getLog(ViewDatasetWidget.class);
	private ViewDatasetWidgetController controller;


	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ViewDatasetWidgetModel createModel() {
		return new ViewDatasetWidgetModel(this);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewDatasetWidgetRenderer createRenderer() {
		return new ViewDatasetWidgetRenderer();
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model and renderer
		
		this.controller = new ViewDatasetWidgetController(this, this.model);
		// TODO memory
		
		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(ViewDatasetWidget.class);
	}




	


	/**
	 * @param id
	 */
	public void setCurrentEntry(String id) {
		log.enter("setEntry");
		
		// delegate to controller
		this.controller.viewEntry(id);
		
		log.leave();
		
	}



	
	// TODO adders for action handlers


	
	
	// TODO memory

}
