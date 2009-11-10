/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class UploadDataFileRevisionWidget extends
		DelegatingWidget<AsyncWidgetModel, UploadDataFileRevisionWidgetRenderer> {
	
	
	
	
	
	private Log log;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		this.model = new AsyncWidgetModel(this);
		this.renderer = new UploadDataFileRevisionWidgetRenderer(this);
		this.renderer.setCanvas(this.contentBox);
		
		log.leave();
	}





	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(UploadDataFileRevisionWidget.class);
	}

	
	
	
	public HandlerRegistration addSuccessHandler(UploadDataFileRevisionSuccessHandler h) {
		return this.addHandler(h, UploadDataFileRevisionSuccessEvent.TYPE);
	}
	
	
	
}
