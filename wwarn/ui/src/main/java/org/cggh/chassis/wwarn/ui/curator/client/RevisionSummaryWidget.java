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
public class RevisionSummaryWidget 
	extends DelegatingWidget<RevisionSummaryWidgetModel, RevisionSummaryWidgetRenderer> {

	private static final Log log = LogFactory.getLog(RevisionSummaryWidget.class);
	

	private RevisionSummaryWidgetController controller;
	
	@Override
	protected RevisionSummaryWidgetModel createModel() {
		return new RevisionSummaryWidgetModel();
	}

	public RevisionSummaryWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected RevisionSummaryWidgetRenderer createRenderer() {
		return new RevisionSummaryWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new RevisionSummaryWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
