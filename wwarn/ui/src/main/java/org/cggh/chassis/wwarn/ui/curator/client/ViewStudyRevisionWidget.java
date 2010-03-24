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
public class ViewStudyRevisionWidget 
	 	extends DelegatingWidget<ViewStudyRevisionWidgetModel, ViewStudyRevisionWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ViewStudyRevisionWidget.class);
	

	public final WidgetEventChannel studyRevisionActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studyRevisionActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studyRevisionActionsNextRevisionNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studyRevisionActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studyRevisionActionsPreviousRevisionNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studyRevisionActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	
	private ViewStudyRevisionWidgetController controller;
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel viewStudyRevisionListStudiesNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyRevisionEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyRevisionNextRevisionNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyRevisionViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyRevisionPreviousRevisionNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyRevisionListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);




	// public so as to be available to renderer 
	public StudyRevisionActionsWidget studyRevisionActionsWidget;


	// public so as to be available to renderer 
	public ViewRevisionQuestionnaireWidget viewRevisionQuestionnaireWidget;


	// public so as to be available to renderer 
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
		
		// TODO refresh this
		log.leave();	
	}
	
	
	
}
