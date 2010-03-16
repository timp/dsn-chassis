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
public class ListStudyRevisionsWidget 
	 	extends DelegatingWidget<ListStudyRevisionsWidgetModel, ListStudyRevisionsWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ListStudyRevisionsWidget.class);
	
	private ListStudyRevisionsWidgetController controller;
	private StudySummaryWidget studySummaryWidget;

	private StudyRevisionListWidget studyRevisionListWidget;

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

	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
