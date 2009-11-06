/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;


/**
 * @author raok
 *
 */
public class NewSubmissionWidget 
	extends DelegatingWidget<AsyncWidgetModel, NewSubmissionWidgetRenderer> {
	
	
	
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private NewSubmissionWidgetController controller; 
	
	
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewSubmissionWidget.class);
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		this.model = new AsyncWidgetModel(this);
		this.controller = new NewSubmissionWidgetController(this, this.model);
		this.renderer = new NewSubmissionWidgetRenderer(this);
		this.renderer.setCanvas(this.contentBox);
		this.renderer.setController(this.controller);
		
		log.leave();
	}

	
	
	
	

	/**
	 * Register handler for create success event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addCreateSubmissionSuccessHandler(CreateSubmissionSuccessHandler h) {
		return this.addHandler(h, CreateSubmissionSuccessEvent.TYPE);
	}
	
	
	
	
	

	/**
	 * 
	 */
	public void refreshStudies() {
		this.renderer.getForm().refreshStudies();
	}

	
	


}
