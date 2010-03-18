/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.ErrorHandler;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.wwarn.ui.submitter.client.AddInformationWidget;
import org.cggh.chassis.wwarn.ui.submitter.client.BackToStartNavigationEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.BackToStartNavigationHandler;
import org.cggh.chassis.wwarn.ui.submitter.client.HomeNavigationEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.HomeNavigationHandler;
import org.cggh.chassis.wwarn.ui.submitter.client.ProceedActionEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.ProceedActionHandler;
import org.cggh.chassis.wwarn.ui.submitter.client.SelectStudyWidget;
import org.cggh.chassis.wwarn.ui.submitter.client.StepBackNavigationEvent;
import org.cggh.chassis.wwarn.ui.submitter.client.StepBackNavigationHandler;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitWidget;
import org.cggh.chassis.wwarn.ui.submitter.client.SubmitterHomeWidget;
import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidget;
import org.cggh.chassis.wwarn.ui.submitter.client.UploadFilesWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;

/**
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


	
	@Override
	public void renderMainChildren() {
		log.enter("renderMainChildren");
		
		this.curatorHomeWidget = new CuratorHomeWidget();
		this.mainChildren.add(this.curatorHomeWidget);

		
		this.defaultChild = this.curatorHomeWidget;
		this.curatorHomeWidget.refresh();

		log.leave();

	}

	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = curatorHomeWidget.viewStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				// FIXME
				//viewStudyWidget.setStudy(e.getStudyUri());
				viewStudyWidget.refresh();
				setActiveChild(viewStudyWidget);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);

	}
	
	
	
	
	
	

	

}
