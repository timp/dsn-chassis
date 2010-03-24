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
public class ListStudyRevisionsWidget 
	 	extends DelegatingWidget<ListStudyRevisionsWidgetModel, ListStudyRevisionsWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ListStudyRevisionsWidget.class);
	

	public final WidgetEventChannel studySummaryViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studySummaryListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studyRevisionListViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);
	

	public final WidgetEventChannel studyRevisionListViewRevisionNavigationEventChannel = new WidgetEventChannel(this);
	
	private ListStudyRevisionsWidgetController controller;
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();

	public final WidgetEventChannel listStudyRevisionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudyRevisionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudyRevisionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudyRevisionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudyRevisionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudyRevisionsViewCurrentStudyNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudyRevisionsViewRevisionNavigationEventChannel = new WidgetEventChannel(this);




	// public so as to be available to renderer 
	public StudySummaryWidget studySummaryWidget;


	// public so as to be available to renderer 
	public StudyRevisionListWidget studyRevisionListWidget;

	@Override
	protected ListStudyRevisionsWidgetModel createModel() {
		return new ListStudyRevisionsWidgetModel();
	}

	public ListStudyRevisionsWidgetModel getModel() {
		return model;
	}

	@Override
	protected ListStudyRevisionsWidgetRenderer createRenderer() {
		return new ListStudyRevisionsWidgetRenderer(this);
	}
	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		

		this.controller = new ListStudyRevisionsWidgetController(this, this.model);
		this.renderer.setController(controller);

	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		
		// TODO refresh this
		log.leave();	
	}
	
	
	
}
