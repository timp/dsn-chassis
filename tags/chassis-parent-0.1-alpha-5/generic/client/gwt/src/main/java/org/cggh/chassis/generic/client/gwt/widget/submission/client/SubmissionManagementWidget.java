/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class SubmissionManagementWidget 
	extends MultiWidget {

	
	
	
	
	// utility fields
	private Log log = LogFactory.getLog(SubmissionManagementWidget.class);
	
	
	
	
	// UI fields
	ViewSubmissionsPendingReviewWidget vsprWidget;
	ViewSubmissionWidget viewSubmissionWidget;
	ViewDataFileWidget viewDataFileWidget;
	ViewStudyWidget viewStudyWidget;
	
	
	public SubmissionManagementWidget() {
		super(false, true);
	}
	
	
	
	

	@Override
	protected void renderMainChildren() {
		log.enter("renderMainChildren");
		
		this.vsprWidget = new ViewSubmissionsPendingReviewWidget();
		this.viewSubmissionWidget = new ViewSubmissionWidget();
		this.viewDataFileWidget = new CustomViewDataFileWidget();
		this.viewStudyWidget = new CustomViewStudyWidget();
		
		this.mainChildren.add(this.vsprWidget);
		this.mainChildren.add(this.viewSubmissionWidget);
		this.mainChildren.add(this.viewDataFileWidget);
		this.mainChildren.add(this.viewStudyWidget);
		
		log.leave();
	}

	
	
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		HandlerRegistration a = this.vsprWidget.addViewSubmissionActionHandler(new SubmissionActionHandler() {
			
			public void onAction(SubmissionActionEvent e) {

				viewSubmissionWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewSubmissionWidget);
				
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		
		HandlerRegistration b = this.viewSubmissionWidget.addViewDataFileActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {
				
				viewDataFileWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDataFileWidget);

			}
		});

		this.childWidgetEventHandlerRegistrations.add(b);
		
		HandlerRegistration c = this.viewSubmissionWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {

				viewStudyWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewStudyWidget);
				
			}
		});

		this.childWidgetEventHandlerRegistrations.add(c);

	}

	
	
	
	
	@Override
	protected void renderMenuBar() {
		log.enter("renderMenuBar");
		
		Command viewSubmissionsPendingReviewCommand = new Command() { 
			public void execute() { 

				setActiveChild(vsprWidget);
				fireEvent(new MenuEvent());
				
			} 
		};

		MenuItem viewSubmissionsPendingReviewMenuItem = new MenuItem("submissions pending review", viewSubmissionsPendingReviewCommand );
		this.menu.addItem(viewSubmissionsPendingReviewMenuItem);
		
		log.leave();
	}


	
	
	@Override
	protected void setActiveChild(Widget child, boolean memorise) {
		super.setActiveChild(child, memorise);
		
		if (child instanceof ViewSubmissionsPendingReviewWidget) {
			ViewSubmissionsPendingReviewWidget w = (ViewSubmissionsPendingReviewWidget) child;
			w.refresh();
		}
		
	}


	
	
}
