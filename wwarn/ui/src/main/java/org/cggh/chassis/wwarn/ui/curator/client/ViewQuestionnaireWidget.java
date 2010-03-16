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
public class ViewQuestionnaireWidget 
	extends DelegatingWidget<ViewQuestionnaireWidgetModel, ViewQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewQuestionnaireWidget.class);
	

	private ViewQuestionnaireWidgetController controller;
	
	@Override
	protected ViewQuestionnaireWidgetModel createModel() {
		return new ViewQuestionnaireWidgetModel();
	}

	public ViewQuestionnaireWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected ViewQuestionnaireWidgetRenderer createRenderer() {
		return new ViewQuestionnaireWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new ViewQuestionnaireWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
