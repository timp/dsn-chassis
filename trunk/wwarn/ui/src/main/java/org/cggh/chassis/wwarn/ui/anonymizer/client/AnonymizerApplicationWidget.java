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
	
	//TODO: Move these into the home widget when events are wired up.
	private FilesToReviewWidget filesToReviewWidget;
	private FilesToCleanWidget filesToCleanWidget;
	
	private AnonymizerHomeWidget anonymizerHomeWidget;
	private ReviewFileWidget reviewFileWidget;
	//private CleanFileWidget cleanFileWidget;
	
	@Override
	public void renderMainChildren() {
		
		log.enter("renderMainChildren");
		
		this.anonymizerHomeWidget = new AnonymizerHomeWidget();
		this.mainChildren.add(this.anonymizerHomeWidget);
		
		//TODO: Move these into the home widget when events are wired up. 
		this.filesToReviewWidget = new FilesToReviewWidget();
		this.filesToCleanWidget = new FilesToCleanWidget();
		this.mainChildren.add(this.filesToReviewWidget);		
		this.mainChildren.add(this.filesToCleanWidget);		
		
		this.reviewFileWidget = new ReviewFileWidget();
		this.mainChildren.add(this.reviewFileWidget);	
		//this.cleanFileWidget = new CleanFileWidget();
		//this.mainChildren.add(this.cleanFileWidget);	
		
		//TODO: Switch this over to the home widget when events are wired up. 
		this.defaultChild = this.filesToReviewWidget;
		this.anonymizerHomeWidget.refresh();
		this.filesToReviewWidget.refresh();
		this.filesToCleanWidget.refresh();
		
		log.leave();

	}

	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();	
		
		//TODO: Switch this over to the home widget when events are wired up. 
		HandlerRegistration a = filesToReviewWidget.reviewFileNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				
				log.debug("Setting fileToBeReviewed...");
				
				reviewFileWidget.setFileToBeReviewedEntryElement(filesToReviewWidget.getFileToBeReviewedEntryElement());
				
				reviewFileWidget.refresh();
				setActiveChild(reviewFileWidget);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);		

		
		
		HandlerRegistration d2 = this.reviewFileWidget.addBackToStartNavigationHandler(new BackToStartNavigationHandler() {
			
			public void onNavigation(BackToStartNavigationEvent e) {
				filesToReviewWidget.refresh();
				setActiveChild(filesToReviewWidget);
			}

		});		
		this.childWidgetEventHandlerRegistrations.add(d2);
		
	}
	
	
}
