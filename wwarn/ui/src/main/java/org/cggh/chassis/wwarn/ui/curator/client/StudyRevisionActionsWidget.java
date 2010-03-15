/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

/**
 * @author timp
 *
 */
public class StudyRevisionActionsWidget 
	extends DelegatingWidget<StudyRevisionActionsWidgetModel, StudyRevisionActionsWidgetRenderer> {

	private static final Log log = LogFactory.getLog(StudyRevisionActionsWidget.class);
	

	private StudyRevisionActionsWidgetController controller;
		
   
	@Override
	protected StudyRevisionActionsWidgetModel createModel() {
		return new StudyRevisionActionsWidgetModel();
	}

	public StudyRevisionActionsWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected StudyRevisionActionsWidgetRenderer createRenderer() {
		return new StudyRevisionActionsWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new StudyRevisionActionsWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
