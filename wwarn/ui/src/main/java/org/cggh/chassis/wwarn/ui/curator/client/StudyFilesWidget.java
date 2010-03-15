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
public class StudyFilesWidget 
	extends DelegatingWidget<StudyFilesWidgetModel, StudyFilesWidgetRenderer> {

	private static final Log log = LogFactory.getLog(StudyFilesWidget.class);
	

	private StudyFilesWidgetController controller;
		
   
	@Override
	protected StudyFilesWidgetModel createModel() {
		return new StudyFilesWidgetModel();
	}

	public StudyFilesWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected StudyFilesWidgetRenderer createRenderer() {
		return new StudyFilesWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new StudyFilesWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
