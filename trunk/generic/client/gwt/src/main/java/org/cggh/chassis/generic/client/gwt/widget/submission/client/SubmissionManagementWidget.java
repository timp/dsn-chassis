/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.review.ReviewEntry;
import org.cggh.chassis.generic.atomui.client.CreateSuccessEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.CancelHandler;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
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
	ReviewSubmissionWidget reviewSubmissionWidget;
	
	
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
		this.reviewSubmissionWidget = new ReviewSubmissionWidget();
		
		this.mainChildren.add(this.vsprWidget);
		this.mainChildren.add(this.viewSubmissionWidget);
		this.mainChildren.add(this.viewDataFileWidget);
		this.mainChildren.add(this.viewStudyWidget);
		this.mainChildren.add(this.reviewSubmissionWidget);
		
		log.leave();
	}

	
	
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		
		this.childWidgetEventHandlerRegistrations.add(
				this.vsprWidget.addViewSubmissionActionHandler(new SubmissionActionHandler() {
			
			public void onAction(SubmissionActionEvent e) {

				viewSubmissionWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewSubmissionWidget);
				
			}
		}));

		

		this.childWidgetEventHandlerRegistrations.add(
				this.viewSubmissionWidget.addViewDataFileActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {
				
				viewDataFileWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewDataFileWidget);

			}
		}));

		

		this.childWidgetEventHandlerRegistrations.add(
				this.viewSubmissionWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {

				viewStudyWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewStudyWidget);
				
			}
		}));


		this.childWidgetEventHandlerRegistrations.add(
				this.viewSubmissionWidget.addReviewSubmissionActionHandler(new SubmissionActionHandler() {
					
					public void onAction(SubmissionActionEvent e) {

						reviewSubmissionWidget.retrieveSubmissionEntry(e.getEntry().getId());

						setActiveChild(reviewSubmissionWidget);
						
					}
				}));
				
		
		this.childWidgetEventHandlerRegistrations.add(
				this.reviewSubmissionWidget.addCancelHandler(new CancelHandler() {

			public void onCancel(CancelEvent e) {
				log.enter("anonymous cancel");
				History.back();
				log.leave();
			}
		}));
		
		
        this.childWidgetEventHandlerRegistrations.add(
        		this.reviewSubmissionWidget.addCreateReviewSuccessHandler(
        				new CreateReviewSuccessHandler() {

					public void onCreateSuccess(
							CreateSuccessEvent<ReviewEntry> e) {

//						viewSubmissionWidget.viewEntry(e.getEntry().getId()); // N.B. this is wrong, asking the view submission widget to view a review entry

						viewSubmissionWidget.refresh();
						setActiveChild(viewSubmissionWidget);
						
					} }));

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
