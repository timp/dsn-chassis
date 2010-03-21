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
public class ViewStudyQuestionnaireWidget 
	 	extends DelegatingWidget<ViewStudyQuestionnaireWidgetModel, ViewStudyQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyQuestionnaireWidget.class);
	
	private ViewStudyQuestionnaireWidgetController controller;



	public StudySummaryWidget studySummaryWidget;


	public ViewQuestionnaireWidget viewQuestionnaireWidget;

	@Override
	protected ViewStudyQuestionnaireWidgetModel createModel() {
		return new ViewStudyQuestionnaireWidgetModel();
	}

	public ViewStudyQuestionnaireWidgetModel getModel() {
		return model;
	}

	@Override
	protected ViewStudyQuestionnaireWidgetRenderer createRenderer() {
		return new ViewStudyQuestionnaireWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new ViewStudyQuestionnaireWidgetController(this, this.model);
		this.renderer.setController(controller);

	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		// TODO get data 
		log.leave();	
	}
	
	
	
	

	

}
