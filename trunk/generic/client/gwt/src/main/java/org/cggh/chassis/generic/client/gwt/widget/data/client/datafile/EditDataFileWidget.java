/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.client.gwt.widget.data.client.DataManagementWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class EditDataFileWidget 
	extends DelegatingWidget<EditDataFileWidgetModel, EditDataFileWidgetRenderer> {
	private Log log = LogFactory.getLog(EditDataFileWidget.class);
	private EditDataFileWidgetController controller;


	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected EditDataFileWidgetModel createModel() {
		return new EditDataFileWidgetModel(this);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected EditDataFileWidgetRenderer createRenderer() {
		return new EditDataFileWidgetRenderer(this);
	}
	
	
	
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate model and renderer
		
		this.controller = new EditDataFileWidgetController(this, this.model);
		this.renderer.setController(this.controller);

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataManagementWidget.class);
	}




	/**
	 * @param entry
	 */
	public void setEntry(DataFileEntry entry) {
		log.enter("setEntry");
		
		this.model.setEntry(entry);
		// shouldn't have to do anything else
		
		log.leave();
	}




	
	/**
	 * Register handler for create success event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addSuccessHandler(UpdateDataFileSuccessHandler h) {
		return this.addHandler(h, UpdateDataFileSuccessEvent.TYPE);
	}





}
