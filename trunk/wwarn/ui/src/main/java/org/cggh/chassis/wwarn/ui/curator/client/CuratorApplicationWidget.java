/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.event.shared.HandlerRegistration;

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
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = curatorHomeWidget.viewStudyNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				
				log.enter("onEvent");

				if (e instanceof ViewStudyNavigationEvent) {
					
					log.debug("Setting fileToBeReviewed...");
					
					// TODO I think we should have an Element here, but the mock only has an id
					
					//viewStudyWidget.setStudy(((ViewStudyNavigationEvent) e).getStudy());
					viewStudyWidget.setStudyID(((ViewStudyNavigationEvent) e).getStudyId());
					
					viewStudyWidget.refresh();
					setActiveChild(viewStudyWidget);
				
				} else {
					
					log.debug(" event not an instanceof ViewStudyNavigationEvent");
				}				
				
				log.leave();
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);


	}
	
	
	@Override
	public void refresh() {
		this.curatorHomeWidget.refresh();
	}
	
	

	

}
