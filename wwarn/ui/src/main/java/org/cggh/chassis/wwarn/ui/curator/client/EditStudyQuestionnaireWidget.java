package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;


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
public class EditStudyQuestionnaireWidget 
	 	extends DelegatingWidget<EditStudyQuestionnaireWidgetModel, EditStudyQuestionnaireWidgetRenderer> {

	private static final Log log = LogFactory.getLog(EditStudyQuestionnaireWidget.class);
	
	private EditStudyQuestionnaireWidgetController controller;
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	public final WidgetEventChannel studyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel editQuestionnaireSaveStudyEventChannel = new WidgetEventChannel(this);



	// public so as to be available to renderer 
	public StudySummaryWidget studySummaryWidget;


	// public so as to be available to renderer 
	public EditQuestionnaireWidget editQuestionnaireWidget;


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
		this.renderer.setController(controller);

	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this
		log.leave();	
	}
	
	
	
}
