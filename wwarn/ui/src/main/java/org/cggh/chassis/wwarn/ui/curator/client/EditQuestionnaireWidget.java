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
public class EditQuestionnaireWidget 
	extends DelegatingWidget<EditQuestionnaireWidgetModel, EditQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(EditQuestionnaireWidget.class);
	

	private EditQuestionnaireWidgetController controller;
	
	@Override
	protected EditQuestionnaireWidgetModel createModel() {
		return new EditQuestionnaireWidgetModel();
	}

	public EditQuestionnaireWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected EditQuestionnaireWidgetRenderer createRenderer() {
		return new EditQuestionnaireWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new EditQuestionnaireWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
