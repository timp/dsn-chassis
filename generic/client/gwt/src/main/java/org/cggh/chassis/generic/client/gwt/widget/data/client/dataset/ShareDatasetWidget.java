/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;



/**
 * @author aliman
 *
 */
public class ShareDatasetWidget 
	extends DelegatingWidget<ShareDatasetWidgetModel, ShareDatasetWidgetRenderer> {

	
	
	
	Log log = LogFactory.getLog(ShareDatasetWidget.class);
	private ShareDatasetWidgetController controller;
	
	

	
	@Override
	public void init() {
		super.init(); // will instantiate model and renderer
		
		this.controller = new ShareDatasetWidgetController(this, this.model);
		
	}
	
	
	

	/**
	 * @param id
	 */
	public void retrieveEntry(String id) {
		log.enter("retrieveEntry");
		
		this.controller.retrieveEntry(id);
		
		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ShareDatasetWidgetModel createModel() {
		return new ShareDatasetWidgetModel();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ShareDatasetWidgetRenderer createRenderer() {
		return new ShareDatasetWidgetRenderer();
	}
	
	
	
	
}
