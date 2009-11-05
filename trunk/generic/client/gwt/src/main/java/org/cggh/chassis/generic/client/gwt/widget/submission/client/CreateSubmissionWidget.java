/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.DelegatingChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;


/**
 * @author raok
 *
 */
public class CreateSubmissionWidget 
	extends DelegatingChassisWidget<AsyncWidgetModel, CreateSubmissionWidgetRenderer> {
	
	
	
	public static final String NAME = "createSubmissionWidget";
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private CreateSubmissionWidgetController controller; 
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return NAME;
	}

	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(CreateSubmissionWidget.class);
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		this.model = new AsyncWidgetModel(this);
		this.controller = new CreateSubmissionWidgetController(this, this.model);
		this.renderer = new CreateSubmissionWidgetRenderer(this);
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
