/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomui.client.CreateSuccessEvent;
import org.cggh.chassis.generic.atomui.client.CreateSuccessHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;




/**
 * @author aliman
 *
 */
public class NewDataFileWidget 
	extends DelegatingWidget<AsyncWidgetModel, NewDataFileWidgetRenderer> {

	
	
	
	
	
	private Log log;

	
	
	
	protected void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewDataFileWidget.class);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AsyncWidgetModel createModel() {
		return new AsyncWidgetModel();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected NewDataFileWidgetRenderer createRenderer() {
		return new NewDataFileWidgetRenderer(this);
	}
	
	
	
	
	
	

	/**
	 * Register handler for create success event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addCreateSuccessHandler(CreateSuccessHandler<DataFileEntry> h) {
		return this.addHandler(h, CreateSuccessEvent.TYPE);
	}





}
