/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidgetRenderer;

/**
 * @author aliman
 *
 */
public class CustomViewDataFileWidget extends ViewDataFileWidget {

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.DelegatingWidget#createRenderer()
	 */
	@Override
	protected ViewDataFileWidgetRenderer createRenderer() {
		return new CustomViewDataFileWidgetRenderer(this);
	}


	

}
