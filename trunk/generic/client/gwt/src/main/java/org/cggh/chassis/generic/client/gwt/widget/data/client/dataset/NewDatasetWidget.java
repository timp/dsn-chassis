/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQuery;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
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
public class NewDatasetWidget 
//	extends DelegatingWidget<AsyncWidgetModel, NewDatasetWidgetRenderer> 
	extends AtomCrudWidget<DatasetEntry, DatasetFeed, DatasetQuery, AtomCrudWidgetModel<DatasetEntry>, NewDatasetWidgetRenderer, NewDatasetWidgetController>

{

	
	
	

	
	
	
	private Log log;


	
	

	@Override
	protected AtomCrudWidgetModel<DatasetEntry> createModel() {
		return new AtomCrudWidgetModel<DatasetEntry>();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected NewDatasetWidgetRenderer createRenderer() {
		return new NewDatasetWidgetRenderer(this);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidget#createController()
	 */
	@Override
	protected NewDatasetWidgetController createController() {
		return new NewDatasetWidgetController(this, this.model);
	}





	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		super.init(); // will instantiate model, renderer and controller
		
		this.renderer.setController(this.controller);
		
		log.leave();
	}
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewDatasetWidget.class);
	}




}
