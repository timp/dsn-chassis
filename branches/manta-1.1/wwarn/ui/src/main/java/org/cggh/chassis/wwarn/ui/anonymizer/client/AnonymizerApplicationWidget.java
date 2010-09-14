package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.wwarn.ui.anonymizer.client.BackToStartNavigationEvent;
import org.cggh.chassis.wwarn.ui.anonymizer.client.BackToStartNavigationHandler;

import com.google.gwt.event.shared.HandlerRegistration;


public class AnonymizerApplicationWidget extends MultiWidget {

	private Log log = LogFactory.getLog(AnonymizerApplicationWidget.class);

	
	private AnonymizerHomeWidget anonymizerHomeWidget;
	private ReviewFileWidget reviewFileWidget;
	private CleanFileWidget cleanFileWidget;
	
	@Override
	public void renderMainChildren() {
		
		log.enter("renderMainChildren");
		
		this.anonymizerHomeWidget = new AnonymizerHomeWidget();
		this.mainChildren.add(this.anonymizerHomeWidget);
		
		this.reviewFileWidget = new ReviewFileWidget();
		this.mainChildren.add(this.reviewFileWidget);	
		this.cleanFileWidget = new CleanFileWidget();
		this.mainChildren.add(this.cleanFileWidget);	

		this.defaultChild = this.anonymizerHomeWidget;
		
		// Shouldn't refresh here, due to the sequence of events
		// this would cause try to render the HomeWidget before its children have been built
//		this.anonymizerHomeWidget.refresh();

		
		log.leave();

	}

	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();	
		

		HandlerRegistration a = this.anonymizerHomeWidget.reviewFileNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				
				log.enter("onEvent");
				
				if (e instanceof ReviewFileNavigationEvent) {
					
					log.debug("Setting fileToBeReviewed...");
					
					reviewFileWidget.setFileToBeReviewedEntryElement(((ReviewFileNavigationEvent) e).getFileToBeReviewedEntryElement());
					
					reviewFileWidget.refresh();
					setActiveChild(reviewFileWidget);
				
				} else {
					
					log.debug(" event not an instanceof ReviewFileNavigationEvent");
				}
				
				log.leave();
	
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);		

		HandlerRegistration d2 = this.reviewFileWidget.addBackToStartNavigationHandler(new BackToStartNavigationHandler() {
			
			public void onNavigation(BackToStartNavigationEvent e) {
				anonymizerHomeWidget.refresh();
				setActiveChild(anonymizerHomeWidget);
			}

		});		
		this.childWidgetEventHandlerRegistrations.add(d2);


		
		HandlerRegistration b = this.anonymizerHomeWidget.cleanFileNavigationEventChannel.addHandler(new WidgetEventHandler() {
			
			public void onEvent(WidgetEvent e) {
				
				log.enter("onEvent");
				
				if (e instanceof CleanFileNavigationEvent) {
					
					log.debug("Setting cleanToBeReviewed...");
					
					cleanFileWidget.setFileToBeCleanedEntryElement(((CleanFileNavigationEvent) e).getFileToBeCleanedEntryElement());
					
					cleanFileWidget.refresh();
					setActiveChild(cleanFileWidget);
				
				} else {
					
					log.debug(" event not an instanceof CleanFileNavigationEvent");
				}
				
				log.leave();
	
			}
		});
		this.childWidgetEventHandlerRegistrations.add(b);			
		
		HandlerRegistration b2 = this.cleanFileWidget.addBackToStartNavigationHandler(new BackToStartNavigationHandler() {
			
			public void onNavigation(BackToStartNavigationEvent e) {
				anonymizerHomeWidget.refresh();
				setActiveChild(anonymizerHomeWidget);
			}

		});		
		this.childWidgetEventHandlerRegistrations.add(b2);	
			
		
	}
	
	
	
	@Override
	public void refresh() {
		this.anonymizerHomeWidget.refresh();
	}
	
}
