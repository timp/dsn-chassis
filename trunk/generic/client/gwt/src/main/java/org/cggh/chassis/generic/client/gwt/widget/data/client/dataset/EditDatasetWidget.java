/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atom.client.UpdateSuccessHandler;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class EditDatasetWidget 
	extends DelegatingWidget<EditDatasetWidgetModel, EditDatasetWidgetRenderer> {
	
	
	
	
	
	
	private Log log = LogFactory.getLog(EditDatasetWidget.class);
	private EditDatasetWidgetController controller;





	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(EditDatasetWidget.class);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected EditDatasetWidgetModel createModel() {
		return new EditDatasetWidgetModel();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected EditDatasetWidgetRenderer createRenderer() {
		return new EditDatasetWidgetRenderer(this);
	}



	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model and renderer
		
		this.controller = new EditDatasetWidgetController(this, this.model);
		this.renderer.setController(this.controller);

		log.leave();
	}
	
	
	


	/**
	 * @param entry
	 */
	public void setEntry(DatasetEntry entry) {
		this.model.setEntry(entry);
	}




	/**
	 * Register handler for create success event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addUpdateSuccessHandler(UpdateSuccessHandler<DatasetEntry> h) {
		return this.addHandler(h, UpdateDatasetSuccessEvent.TYPE);
	}







}
