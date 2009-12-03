/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.h3;
import static org.cggh.chassis.generic.widget.client.HtmlElements.p;

import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionsWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileDatasetsWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidgetRenderer;

/**
 * @author aliman
 *
 */
public class CustomViewDataFileWidgetRenderer 
	extends ViewDataFileWidgetRenderer {

	
	
	
	/**
	 * @param owner
	 */
	public CustomViewDataFileWidgetRenderer(ViewDataFileWidget owner) {
		super(owner);
	}

	

	@Override
	protected void setMainPanelStyle() {
		// do nothing
	}

	
	
	
	@Override
	protected void renderActionsWidget() {
		// do nothing
	}
	
	
	
	
	@Override
	protected void renderDatasetsSection() {
		// do nothing
	}


}
