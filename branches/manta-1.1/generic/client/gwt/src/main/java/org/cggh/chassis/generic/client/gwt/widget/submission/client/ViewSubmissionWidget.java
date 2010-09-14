/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetMemory;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyActionEvent;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidget 
	extends AtomCrudWidget<SubmissionEntry, 
							SubmissionFeed, 
							SubmissionQuery, 
							AtomCrudWidgetModel<SubmissionEntry>, 
							ViewSubmissionWidgetRenderer, 
							ViewSubmissionWidgetController> 
{

	
	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidget.class);

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AtomCrudWidgetModel<SubmissionEntry> createModel() {
		log.enter("createModel");

		AtomCrudWidgetModel<SubmissionEntry> model = new AtomCrudWidgetModel<SubmissionEntry>();
		
		log.leave();
		return model;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidget#createController()
	 */
	@Override
	protected ViewSubmissionWidgetController createController() {
		log.enter("createController");

		ViewSubmissionWidgetController controller = new ViewSubmissionWidgetController(this, this.model);
		
		log.leave();
		return controller;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewSubmissionWidgetRenderer createRenderer() {
		log.enter("createRenderer");

		ViewSubmissionWidgetRenderer renderer = new ViewSubmissionWidgetRenderer(this);
		
		log.leave();
		return renderer;
	}
	
	

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		
		super.init(); // this will instantiate controller, model and renderer
		
		this.memory = new AtomCrudWidgetMemory<SubmissionEntry, SubmissionFeed>(this.model, this.controller);
		
	}
	
	
	
	
	private void ensureLog() {
		if (this.log == null) this.log = LogFactory.getLog(ViewSubmissionWidget.class);
	}
	
	
	
	
	public void viewEntry(String id) {
		
		this.controller.retrieveExpandedEntry(id);

	}

	
	
	
	@Override
	public void refresh() {
		this.viewEntry(this.model.getEntryId());
		
	}

	
	
	public HandlerRegistration addViewStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyActionEvent.TYPE);
	}



	public HandlerRegistration addViewDataFileActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, ViewDataFileActionEvent.TYPE);
	}


    public HandlerRegistration addReviewSubmissionActionHandler(SubmissionActionHandler h) { 
		return this.addHandler(h, ReviewSubmissionActionEvent.TYPE);    	
    }

	public HandlerRegistration addAssignCuratorActionHandler(SubmissionActionHandler h) {
		return this.addHandler(h, AssignCuratorActionEvent.TYPE);
	}



	
	
}
