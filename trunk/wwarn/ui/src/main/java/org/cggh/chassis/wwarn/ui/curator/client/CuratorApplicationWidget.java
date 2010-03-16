/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 *
 */
public class CuratorApplicationWidget 
	 	extends MultiWidget {

	private static final Log log = LogFactory.getLog(CuratorApplicationWidget.class);
	
	private CuratorHomeWidget curatorHomeWidget;

	private ViewStudyWidget viewStudyWidget;

	private ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget;

	private EditStudyQuestionnaireWidget editStudyQuestionnaireWidget;

	private ListStudyRevisionsWidget listStudyRevisionsWidget;

	private ViewStudyRevisionWidget viewStudyRevisionWidget;


	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
