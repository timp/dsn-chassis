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
public class StudyActionsWidget 
	extends DelegatingWidget<StudyActionsWidgetModel, StudyActionsWidgetRenderer> {

	private static final Log log = LogFactory.getLog(StudyActionsWidget.class);
	

	private StudyActionsWidgetController controller;
		
   
	@Override
	protected StudyActionsWidgetModel createModel() {
		return new StudyActionsWidgetModel();
	}

	public StudyActionsWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected StudyActionsWidgetRenderer createRenderer() {
		return new StudyActionsWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new StudyActionsWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
