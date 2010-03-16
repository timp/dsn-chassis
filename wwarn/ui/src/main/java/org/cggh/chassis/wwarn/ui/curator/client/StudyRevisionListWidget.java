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
public class StudyRevisionListWidget 
	extends DelegatingWidget<StudyRevisionListWidgetModel, StudyRevisionListWidgetRenderer> {

	private static final Log log = LogFactory.getLog(StudyRevisionListWidget.class);
	

	private StudyRevisionListWidgetController controller;
		private CurrentStudyRevisionWidget currentStudyRevisionWidget;

	private PriorStudyRevisionsListWidget priorStudyRevisionsListWidget;


	@Override
	protected StudyRevisionListWidgetModel createModel() {
		return new StudyRevisionListWidgetModel();
	}

	public StudyRevisionListWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected StudyRevisionListWidgetRenderer createRenderer() {
		return new StudyRevisionListWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new StudyRevisionListWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
