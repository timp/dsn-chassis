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
public class PriorStudyRevisionsListWidget 
	extends DelegatingWidget<PriorStudyRevisionsListWidgetModel, PriorStudyRevisionsListWidgetRenderer> {

	private static final Log log = LogFactory.getLog(PriorStudyRevisionsListWidget.class);
	

	private PriorStudyRevisionsListWidgetController controller;
		
	private PriorRevisionRowWidget priorRevisionRowWidget;
   	
	@Override
	protected PriorStudyRevisionsListWidgetModel createModel() {
		return new PriorStudyRevisionsListWidgetModel();
	}

	public PriorStudyRevisionsListWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected PriorStudyRevisionsListWidgetRenderer createRenderer() {
		return new PriorStudyRevisionsListWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new PriorStudyRevisionsListWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
