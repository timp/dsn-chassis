/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQuery;
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
public class ViewDatasetWidget 
	extends AtomCrudWidget<DatasetEntry, DatasetFeed, DatasetQuery, AtomCrudWidgetModel<DatasetEntry>, ViewDatasetWidgetRenderer, ViewDatasetWidgetController>


{

	
	
	
	
	private Log log = LogFactory.getLog(ViewDatasetWidget.class);


	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AtomCrudWidgetModel<DatasetEntry> createModel() {
		return new AtomCrudWidgetModel<DatasetEntry>();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewDatasetWidgetRenderer createRenderer() {
		return new ViewDatasetWidgetRenderer(this);
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidget#createController()
	 */
	@Override
	protected ViewDatasetWidgetController createController() {
		return new ViewDatasetWidgetController(this, this.model);
	}



	

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate controller, model and renderer
		
		this.memory = new AtomCrudWidgetMemory<DatasetEntry, DatasetFeed>(this.model, this.controller);
		
		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(ViewDatasetWidget.class);
	}




	


	/**
	 * @param id
	 */
	public Deferred<DatasetEntry> viewEntry(String id) {

		// delegate to controller
		return this.controller.retrieveExpandedEntry(id);
		
	}



	
	public HandlerRegistration addEditDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, EditDatasetActionEvent.TYPE);
	}




	public HandlerRegistration addViewStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyActionEvent.TYPE);
	}



	public HandlerRegistration addViewDataFileActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, ViewDataFileActionEvent.TYPE);
	}



	public HandlerRegistration addShareDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ShareDatasetActionEvent.TYPE);
	}
	
	

}
