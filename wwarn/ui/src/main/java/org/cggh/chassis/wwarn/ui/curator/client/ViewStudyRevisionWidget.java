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
public class ViewStudyRevisionWidget 
	 	extends DelegatingWidget<ViewStudyRevisionWidgetModel, ViewStudyRevisionWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyRevisionWidget.class);
	
	private ViewStudyRevisionWidgetController controller;



	public StudyRevisionActionsWidget studyRevisionActionsWidget;


	public ViewRevisionQuestionnaireWidget viewRevisionQuestionnaireWidget;


	public StudyRevisionSummaryWidget studyRevisionSummaryWidget;

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
		this.renderer.setController(controller);

	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		// TODO get data 
		log.leave();	
	}
	
	
	
	

	

}
