package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.wwarn.ui.anonymizer.client.AnonymizerHomeWidget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;


public class AnonymizerApplicationWidget extends MultiWidget {

	
	private Log log = LogFactory.getLog(AnonymizerApplicationWidget.class);

	
	private AnonymizerHomeWidget anonymizerHomeWidget;	
	private ReviewFileWidget reviewFileWidget;
	
	@Override
	public void renderMainChildren() {
		
		log.enter("renderMainChildren");
	
		this.anonymizerHomeWidget = new AnonymizerHomeWidget();
		this.mainChildren.add(this.anonymizerHomeWidget);

		this.reviewFileWidget = new ReviewFileWidget();
		this.mainChildren.add(this.reviewFileWidget);		
		
		this.defaultChild = this.anonymizerHomeWidget;
		this.anonymizerHomeWidget.refresh();
		
		log.leave();

	}

	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = anonymizerHomeWidget.reviewFileNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				reviewFileWidget.refresh();
				setActiveChild(reviewFileWidget);
				Window.alert("hello");
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);	

	}
	
	
	
	
}
