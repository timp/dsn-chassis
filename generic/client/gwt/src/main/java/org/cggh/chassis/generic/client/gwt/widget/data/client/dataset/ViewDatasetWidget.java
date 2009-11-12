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
public class ViewDatasetWidget 
	extends DelegatingWidget<ViewDatasetWidgetModel, ViewDatasetWidgetRenderer> {
	private Log log = LogFactory.getLog(ViewDatasetWidget.class);


	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected ViewDatasetWidgetModel createModel() {
		return new ViewDatasetWidgetModel(this);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewDatasetWidgetRenderer createRenderer() {
		return new ViewDatasetWidgetRenderer();
	}




	/**
	 * @param id
	 */
	public void setEntry(String id) {
		log.enter("setEntry");
		
		// TODO Auto-generated method stub
		
		log.leave();
		
	}






}
