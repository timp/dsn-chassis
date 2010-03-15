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
public class CurrentStudyRevisionWidget 
	extends DelegatingWidget<CurrentStudyRevisionWidgetModel, CurrentStudyRevisionWidgetRenderer> {

	private static final Log log = LogFactory.getLog(CurrentStudyRevisionWidget.class);
	

	private CurrentStudyRevisionWidgetController controller;
		
	@Override
	protected CurrentStudyRevisionWidgetModel createModel() {
		return new CurrentStudyRevisionWidgetModel();
	}

	public CurrentStudyRevisionWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected CurrentStudyRevisionWidgetRenderer createRenderer() {
		return new CurrentStudyRevisionWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new CurrentStudyRevisionWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
