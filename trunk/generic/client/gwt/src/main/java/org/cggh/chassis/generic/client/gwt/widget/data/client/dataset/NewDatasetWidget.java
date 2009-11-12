/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class NewDatasetWidget 
	extends DelegatingWidget<AsyncWidgetModel, NewDatasetWidgetRenderer> {

	
	
	

	
	
	
	private Log log;
	private NewDatasetWidgetController controller;


	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AsyncWidgetModel createModel() {
		return new AsyncWidgetModel(this);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected NewDatasetWidgetRenderer createRenderer() {
		return new NewDatasetWidgetRenderer(this);
	}





	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		super.init(); // will instantiate model and renderer
		
		this.controller = new NewDatasetWidgetController(this, this.model);
		this.renderer.setController(this.controller);
		
		log.leave();
	}
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewDatasetWidget.class);
	}

	
	
	
	/**
	 * Register handler for create success event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addSuccessHandler(CreateDatasetSuccessHandler h) {
		return this.addHandler(h, CreateDatasetSuccessEvent.TYPE);
	}






}
