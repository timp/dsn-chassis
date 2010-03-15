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
public class StudyRevisionSummaryWidget 
	extends DelegatingWidget<StudyRevisionSummaryWidgetModel, StudyRevisionSummaryWidgetRenderer> {

	private static final Log log = LogFactory.getLog(StudyRevisionSummaryWidget.class);
	

	private StudyRevisionSummaryWidgetController controller;
		
   
	@Override
	protected StudyRevisionSummaryWidgetModel createModel() {
		return new StudyRevisionSummaryWidgetModel();
	}

	public StudyRevisionSummaryWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected StudyRevisionSummaryWidgetRenderer createRenderer() {
		return new StudyRevisionSummaryWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new StudyRevisionSummaryWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
