/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;



import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetMemory;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetActionEvent;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author raok
 *
 */
public class ViewStudyWidget 
	extends AtomCrudWidget<StudyEntry, StudyFeed, StudyQuery, AtomCrudWidgetModel<StudyEntry>, ViewStudyWidgetRenderer, ViewStudyWidgetController>
	
{

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidget#createController()
	 */
	@Override
	protected ViewStudyWidgetController createController() {
		return new ViewStudyWidgetController(this, model);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AtomCrudWidgetModel<StudyEntry> createModel() {
		return new AtomCrudWidgetModel<StudyEntry>();
	}

	
	
	
	
	@Override
	protected ViewStudyWidgetRenderer createRenderer() {
		return new ViewStudyWidgetRenderer(this);
	}

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {

		super.init(); // this will instantiate controller, model and renderer
		
		this.memory = new AtomCrudWidgetMemory<StudyEntry, StudyFeed>(this.model, this.controller);
		
	}
	
	
	
	
	

	/**
	 * @param id
	 */
	public Deferred<StudyEntry> viewEntry(String id) {

		// delegate to controller
		return this.controller.retrieveExpandedEntry(id);
		
	}



	
	public HandlerRegistration addEditStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, EditStudyActionEvent.TYPE);
	}




	
	public HandlerRegistration addEditStudyQuestionnaireActionHandler(StudyActionHandler h) {
		return this.addHandler(h, EditStudyQuestionnaireActionEvent.TYPE);
	}




	

	public HandlerRegistration addViewStudyQuestionnaireActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyQuestionnaireActionEvent.TYPE);
	}




	public HandlerRegistration addViewDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ViewDatasetActionEvent.TYPE);
	}




	
}
