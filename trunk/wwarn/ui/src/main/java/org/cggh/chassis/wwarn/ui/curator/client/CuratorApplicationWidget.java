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
