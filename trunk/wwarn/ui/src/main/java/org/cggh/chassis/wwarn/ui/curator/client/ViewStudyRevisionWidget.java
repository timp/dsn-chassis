/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

/**
 * @author timp
 *
 */
public class ViewStudyRevisionWidget 
	extends DelegatingWidget<ViewStudyRevisionWidgetModel, ViewStudyRevisionWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyRevisionWidget.class);
	

	private ViewStudyRevisionWidgetController controller;
		private StudySummaryWidget studySummaryWidget;


	@Override
	protected ViewStudyRevisionWidgetModel createModel() {
		return new ViewStudyRevisionWidgetModel();
	}

	public ViewStudyRevisionWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected ViewStudyRevisionWidgetRenderer createRenderer() {
		return new ViewStudyRevisionWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new ViewStudyRevisionWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
