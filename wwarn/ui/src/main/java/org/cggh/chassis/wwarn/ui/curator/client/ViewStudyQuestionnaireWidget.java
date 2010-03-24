package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;


import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import com.google.gwt.xml.client.Element;

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
	

	public final WidgetEventChannel studySummaryViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	
	private ViewStudyQuestionnaireWidgetController controller;
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel viewStudyQuestionnaireViewStudyNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyQuestionnaireEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyQuestionnaireListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyQuestionnaireViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyQuestionnaireListStudiesNavigationEventChannel = new WidgetEventChannel(this);




	// public so as to be available to renderer 
	public StudySummaryWidget studySummaryWidget;


	// public so as to be available to renderer 
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
		
		// TODO refresh this
		log.leave();	
	}
	
	
	
}
