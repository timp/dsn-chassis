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
public class PriorRevisionRowWidget 
	extends DelegatingWidget<PriorRevisionRowWidgetModel, PriorRevisionRowWidgetRenderer> {

	private static final Log log = LogFactory.getLog(PriorRevisionRowWidget.class);
	

	private PriorRevisionRowWidgetController controller;
	
	@Override
	protected PriorRevisionRowWidgetModel createModel() {
		return new PriorRevisionRowWidgetModel();
	}

	public PriorRevisionRowWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected PriorRevisionRowWidgetRenderer createRenderer() {
		return new PriorRevisionRowWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new PriorRevisionRowWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
