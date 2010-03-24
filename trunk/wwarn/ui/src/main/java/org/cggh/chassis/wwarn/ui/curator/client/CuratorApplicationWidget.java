/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

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

		this.viewStudyWidget = new ViewStudyWidget();
		this.mainChildren.add(this.viewStudyWidget);

		this.viewStudyQuestionnaireWidget = new ViewStudyQuestionnaireWidget();
		this.mainChildren.add(this.viewStudyQuestionnaireWidget);

		this.editStudyQuestionnaireWidget = new EditStudyQuestionnaireWidget();
		this.mainChildren.add(this.editStudyQuestionnaireWidget);

		this.listStudyRevisionsWidget = new ListStudyRevisionsWidget();
		this.mainChildren.add(this.listStudyRevisionsWidget);

		this.viewStudyRevisionWidget = new ViewStudyRevisionWidget();
		this.mainChildren.add(this.viewStudyRevisionWidget);

		this.defaultChild = this.curatorHomeWidget;

		log.leave();

	}

	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		super.registerHandlersForChildWidgetEvents();
		log.debug("Adding list studies view study widget navigation event handler");
		this.childWidgetEventHandlerRegistrations.add(
				curatorHomeWidget.listStudiesViewStudyNavigationEventChannel.addHandler(
						new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				
				log.enter("onEvent");

				Element studyElement =  ((ViewStudyNavigationEvent) e).getStudy();
				log.debug("Setting study to " + studyElement);
				viewStudyWidget.getModel().studyEntry.set(studyElement);
					
				setActiveChild(viewStudyWidget);
				
				log.leave();
			}
		}));

		log.debug("Adding view study list studies widget navigation event handler on " + 
				viewStudyWidget.studyActionsListStudiesNavigationEventChannel);
		this.childWidgetEventHandlerRegistrations.add(
				viewStudyWidget.studyActionsListStudiesNavigationEventChannel.addHandler(
						new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				
				log.enter("onEvent");

				setActiveChild(curatorHomeWidget);
				
				log.leave();
			}
		}));


		log.leave();
	}
	
	
	@Override
	public void refresh() {
		this.curatorHomeWidget.refresh();
	}
	
	
	
	@Override
	public void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		super.setActiveChild(child, memorise);
		
		// override to trigger refresh if set as active child
		if (child == curatorHomeWidget) {
			curatorHomeWidget.refresh();
		}

		log.leave();
	}

	

}
