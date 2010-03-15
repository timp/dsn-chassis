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
public class ViewStudyQuestionnaireWidget 
	extends DelegatingWidget<ViewStudyQuestionnaireWidgetModel, ViewStudyQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyQuestionnaireWidget.class);
	

	private ViewStudyQuestionnaireWidgetController controller;
    private StudySummaryWidget studySummaryWidget;


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


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
