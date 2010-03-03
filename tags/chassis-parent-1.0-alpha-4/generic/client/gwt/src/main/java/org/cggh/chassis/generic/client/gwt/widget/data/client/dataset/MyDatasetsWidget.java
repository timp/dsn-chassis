/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class MyDatasetsWidget 
	extends DelegatingWidget<MyDatasetsWidgetModel, MyDatasetsWidgetRenderer> 


{

	
	
	private Log log = LogFactory.getLog(MyDatasetsWidget.class);
	private MyDatasetsWidgetController controller;




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected MyDatasetsWidgetModel createModel() {
		return new MyDatasetsWidgetModel();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected MyDatasetsWidgetRenderer createRenderer() {
		return new MyDatasetsWidgetRenderer(this);
	}




	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model and renderer

		this.controller = new MyDatasetsWidgetController(this, this.model);

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(MyDatasetsWidget.class);
	}




	/**
	 * 
	 */
	public void refreshDatasets() {
		log.enter("refreshDatasets");
		
		// delegate to controller
		this.controller.refreshDatasets();
		
		log.leave();
	}




	public HandlerRegistration addViewDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ViewDatasetActionEvent.TYPE);
	}





}
