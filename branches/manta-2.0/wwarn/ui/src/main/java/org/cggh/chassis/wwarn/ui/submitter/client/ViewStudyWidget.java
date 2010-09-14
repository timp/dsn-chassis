/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class ViewStudyWidget extends DelegatingWidget<ViewStudyWidgetModel, ViewStudyWidgetRenderer> {

	
	
	private static final Log log = LogFactory.getLog(ViewStudyWidget.class);
	private ViewStudyWidgetController controller;
	
	
	
	@Override
	protected ViewStudyWidgetModel createModel() {
		return new ViewStudyWidgetModel();
	}

	
	
	
	@Override
	protected ViewStudyWidgetRenderer createRenderer() {
		return new ViewStudyWidgetRenderer();
	}

	
	
	public ViewStudyWidget() {
		super();
		
		this.controller = new ViewStudyWidgetController(this, this.model);
	}
	
	
	
    // TODO Delete me ?
	public Deferred<ViewStudyWidget> retrieveStudy(String studyId) {
		log.enter("retrieveStudy");
		
		// delegate to controller
		Deferred<ViewStudyWidget> deferredSelf = this.controller.retrieveStudy(studyId);

		log.leave();
		return deferredSelf;
	}
	
	
	
}
