/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidgetRenderer;

/**
 * @author aliman
 *
 */
public class CustomViewDatasetWidget extends ViewDatasetWidget {

	
	
	@Override
	protected ViewDatasetWidgetRenderer createRenderer() {
		return new CustomViewDatasetWidgetRenderer(this);
	}


	

}
