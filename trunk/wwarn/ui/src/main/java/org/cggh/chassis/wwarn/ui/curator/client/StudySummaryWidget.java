/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

import com.google.gwt.xml.client.Document;

/**
 * @author timp
 *
 */
public class StudySummaryWidget 
	extends DelegatingWidget<StudySummaryWidgetModel, StudySummaryWidgetRenderer> {

	private static final Log log = LogFactory.getLog(StudySummaryWidget.class);
	

	private StudySummaryWidgetController controller;
		
	private StudyActionsWidget studyActionsWidget;
   	
	@Override
	protected StudySummaryWidgetModel createModel() {
		return new StudySummaryWidgetModel();
	}

	public StudySummaryWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected StudySummaryWidgetRenderer createRenderer() {
		return new StudySummaryWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new StudySummaryWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
