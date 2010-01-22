/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetMemory;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetActionEvent;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ViewDataFileWidget 
	extends AtomCrudWidget<DataFileEntry, DataFileFeed, DataFileQuery, AtomCrudWidgetModel<DataFileEntry>, ViewDataFileWidgetRenderer, ViewDataFileWidgetController>
{
	
	
	
	
	
	
	private Log log;


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected AtomCrudWidgetModel<DataFileEntry> createModel() {
		return new AtomCrudWidgetModel<DataFileEntry>();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewDataFileWidgetRenderer createRenderer() {
		return new ViewDataFileWidgetRenderer(this);
	}


	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidget#createController()
	 */
	@Override
	protected ViewDataFileWidgetController createController() {
		return new ViewDataFileWidgetController(this, model);
	}





	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		super.init(); // this will instantiate controller, model and renderer
		
		this.memory = new AtomCrudWidgetMemory<DataFileEntry, DataFileFeed>(this.model, this.controller);

		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(ViewDataFileWidget.class);
	}




	/**
	 * @param dataFileEntry
	 */
	public Deferred<DataFileEntry> viewEntry(String id) {
		log.enter("viewEntry");

		// delegate to controller
		Deferred<DataFileEntry> def = this.controller.retrieveExpandedEntry(id);
		
		log.leave();
		return def;
	}




	public HandlerRegistration addEditDataFileActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, EditDataFileActionEvent.TYPE);
	}





	public HandlerRegistration addUploadRevisionActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, UploadDataFileRevisionActionEvent.TYPE);
	}




	public HandlerRegistration addViewDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ViewDatasetActionEvent.TYPE);
	}






}
