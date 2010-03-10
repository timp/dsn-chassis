package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.ErrorHandler;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;

public class CuratorApplicationWidget extends MultiWidget {

	
	private Log log = LogFactory.getLog(CuratorApplicationWidget.class);
	
	private CuratorHomeWidget curatorHomeWidget;

	
	
	
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
/*		
		HandlerRegistration a = curatorHomeWidget.submitDataNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				selectStudyWidget.setSelectedStudy(null);
				selectStudyWidget.refresh();
				setActiveChild(selectStudyWidget);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);
		
*/		

	}
	
	
	
	
}
