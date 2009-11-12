/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class EditDatasetWidget extends DelegatingWidget {
	private Log log = LogFactory.getLog(EditDatasetWidget.class);





	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(EditDatasetWidget.class);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createModel()
	 */
	@Override
	protected Object createModel() {
		return null;
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ChassisWidgetRenderer createRenderer() {
		return null;
	}






}
