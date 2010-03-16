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
public class EditStudyQuestionnaireWidget 
	 	extends DelegatingWidget<EditStudyQuestionnaireWidgetModel, EditStudyQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(EditStudyQuestionnaireWidget.class);
	
	private EditStudyQuestionnaireWidgetController controller;
	private StudySummaryWidget studySummaryWidget;

	private EditQuestionnaireWidget editQuestionnaireWidget;

	@Override
	protected EditStudyQuestionnaireWidgetModel createModel() {
		return new EditStudyQuestionnaireWidgetModel();
	}

	public EditStudyQuestionnaireWidgetModel getModel() {
		return model;
	}

	@Override
	protected EditStudyQuestionnaireWidgetRenderer createRenderer() {
		return new EditStudyQuestionnaireWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
				this.controller = new EditStudyQuestionnaireWidgetController(this, this.model);

	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
