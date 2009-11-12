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
public class MyDatasetsWidget 
	extends DelegatingWidget<MyDatasetsWidgetModel, MyDatasetsWidgetRenderer> {
	private Log log = LogFactory.getLog(MyDatasetsWidget.class);




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected MyDatasetsWidgetModel createModel() {
		return new MyDatasetsWidgetModel();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected MyDatasetsWidgetRenderer createRenderer() {
		return new MyDatasetsWidgetRenderer();
	}






}
