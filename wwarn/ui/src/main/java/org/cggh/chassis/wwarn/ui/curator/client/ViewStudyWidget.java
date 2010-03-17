/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;


import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class ViewStudyWidget 
	 	extends DelegatingWidget<ViewStudyWidgetModel, ViewStudyWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyWidget.class);
	
	private ViewStudyWidgetController controller;
	private StudySummaryWidget studySummaryWidget;

	private ViewStudyMetadataWidget viewStudyMetadataWidget;

	private ListSubmissionsWidget listSubmissionsWidget;

	private ListCurationsWidget listCurationsWidget;

	@Override
	protected ViewStudyWidgetModel createModel() {
		return new ViewStudyWidgetModel();
	}

	public ViewStudyWidgetModel getModel() {
		return model;
	}

	@Override
	protected ViewStudyWidgetRenderer createRenderer() {
		return new ViewStudyWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
				this.controller = new ViewStudyWidgetController(this, this.model);

	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
