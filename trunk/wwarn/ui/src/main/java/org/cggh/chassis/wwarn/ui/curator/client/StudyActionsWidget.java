/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;import org.cggh.chassis.generic.widget.client.ChassisWidget;


import org.cggh.chassis.generic.widget.client.WidgetEventChannel;


/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class StudyActionsWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(StudyActionsWidget.class);
	



	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		// TODO get data 
		log.leave();	
	}
	
	
	public final WidgetEventChannel viewStudyNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel editStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel viewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);


	public final WidgetEventChannel listStudiesNavigationEventChannel = new WidgetEventChannel(this);


	
	

	

}
