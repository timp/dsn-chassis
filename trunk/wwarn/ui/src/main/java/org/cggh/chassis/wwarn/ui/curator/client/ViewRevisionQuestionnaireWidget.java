/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;


/**
 * @author timp
 *
 */
public class ViewRevisionQuestionnaireWidget 
	extends DelegatingWidget<ViewRevisionQuestionnaireWidgetModel, ViewRevisionQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewRevisionQuestionnaireWidget.class);
	

	private ViewRevisionQuestionnaireWidgetController controller;
		
   
	@Override
	protected ViewRevisionQuestionnaireWidgetModel createModel() {
		return new ViewRevisionQuestionnaireWidgetModel();
	}

	public ViewRevisionQuestionnaireWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected ViewRevisionQuestionnaireWidgetRenderer createRenderer() {
		return new ViewRevisionQuestionnaireWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new ViewRevisionQuestionnaireWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
