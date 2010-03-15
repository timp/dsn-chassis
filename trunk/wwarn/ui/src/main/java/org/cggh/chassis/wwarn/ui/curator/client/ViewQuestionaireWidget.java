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
public class ViewQuestionaireWidget 
	extends DelegatingWidget<ViewQuestionaireWidgetModel, ViewQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewQuestionaireWidget.class);
	

	private ViewQuestionaireWidgetController controller;
		
	@Override
	protected ViewQuestionaireWidgetModel createModel() {
		return new ViewQuestionaireWidgetModel();
	}

	public ViewQuestionaireWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected ViewQuestionnaireWidgetRenderer createRenderer() {
		return new ViewQuestionnaireWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new ViewQuestionaireWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
