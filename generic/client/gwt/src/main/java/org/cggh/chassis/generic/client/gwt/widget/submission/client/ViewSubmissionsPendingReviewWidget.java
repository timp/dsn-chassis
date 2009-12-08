/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewSubmissionsPendingReviewWidget 
	extends DelegatingWidget<ViewSubmissionsPendingReviewWidgetModel, ViewSubmissionsPendingReviewWidgetRenderer> {

	
	
	
	
	Log log = LogFactory.getLog(ViewSubmissionsPendingReviewWidget.class);
	private ViewSubmissionsPendingReviewWidgetController controller;
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ViewSubmissionsPendingReviewWidgetModel createModel() {
		return new ViewSubmissionsPendingReviewWidgetModel();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewSubmissionsPendingReviewWidgetRenderer createRenderer() {
		return new ViewSubmissionsPendingReviewWidgetRenderer(this);
	}
	
	
	
	
	@Override
	public void init() {
		
		super.init(); // this should instantiate model and renderer
		
		this.controller = new ViewSubmissionsPendingReviewWidgetController(this, this.model);
		
	}

	
	
	
	
	@Override
	public void refresh() {
		
		// delegate to controller
		this.controller.refreshSubmissions();
		
	}
	
	
	
	
	public HandlerRegistration addViewSubmissionActionHandler(SubmissionActionHandler h) {
		return this.addHandler(h, ViewSubmissionActionEvent.TYPE);
	}

	
	
	
}
