/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.review.ReviewEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomui.client.CreateSuccessEvent;
import org.cggh.chassis.generic.atomui.client.UpdateSuccessEvent;
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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DialogBox;
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
	ListSubmissionsWidget listSubmissionsWidget;
	ViewSubmissionWidget viewSubmissionWidget;
	ViewDataFileWidget viewDataFileWidget;
	ViewStudyWidget viewStudyWidget;
	ReviewSubmissionWidget reviewSubmissionWidget;
	
	DialogBox selectCuratorDialog;
	SelectCuratorWidget selectCuratorWidget;
	
	
	public SubmissionManagementWidget() {
		super(false, true);
	}
	
	
	
	

	@Override
	public void renderMainChildren() {
		log.enter("renderMainChildren");
		
		this.listSubmissionsWidget = new ListSubmissionsWidget();
		this.viewSubmissionWidget = new ViewSubmissionWidget();
		this.viewDataFileWidget = new CustomViewDataFileWidget();
		this.viewStudyWidget = new CustomViewStudyWidget();
		this.reviewSubmissionWidget = new ReviewSubmissionWidget();
		this.selectCuratorWidget = new SelectCuratorWidget();
		
		this.mainChildren.add(this.listSubmissionsWidget);
		this.mainChildren.add(this.viewSubmissionWidget);
		this.mainChildren.add(this.viewDataFileWidget);
		this.mainChildren.add(this.viewStudyWidget);
		this.mainChildren.add(this.reviewSubmissionWidget);
		this.mainChildren.add(this.selectCuratorWidget);
		
		log.leave();
	}

	
	
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		
		this.childWidgetEventHandlerRegistrations.add(
				this.listSubmissionsWidget.addViewSubmissionActionHandler(new SubmissionActionHandler() {
			
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
						
						viewSubmissionWidget.refresh(); // TODO this causes null pointer exceptions
						setActiveChild(viewSubmissionWidget);
						
					} }));
  
        
        
        
        
        SubmissionActionHandler assignCuratorActionHandler = new SubmissionActionHandler() {

			public void onAction(SubmissionActionEvent e) {

				selectCuratorWidget.retrieveSubmissionEntry(e.getEntry().getEditLink().getHref());
				setActiveChild(selectCuratorWidget);
				
			}
        	
        };
        
        HandlerRegistration a = this.viewSubmissionWidget.addAssignCuratorActionHandler(assignCuratorActionHandler);
        this.childWidgetEventHandlerRegistrations.add(a);
        
        
        UpdateSubmissionSuccessHandler assignCuratorSuccessHandler = new UpdateSubmissionSuccessHandler() {
			
			public void onUpdateSuccess(UpdateSuccessEvent<SubmissionEntry> e) {

				viewSubmissionWidget.refresh(); // TODO this causes null pointer exceptions
				setActiveChild(viewSubmissionWidget);
				
			}
			
		};
		
		HandlerRegistration b = this.selectCuratorWidget.addUpdateSubmissionSuccessHandler(assignCuratorSuccessHandler);
		this.childWidgetEventHandlerRegistrations.add(b);
	}

	
	
	
	
	@Override
	public void renderMenuBar() {
		log.enter("renderMenuBar");
		
		this.menu.addItem(new MenuItem("submissions pending review", 
				listSubmissionsFilteredByExistanceOfReviewCommand(Boolean.FALSE) ));
		
		this.menu.addItem(new MenuItem("accepted submissions ", 
				listSubmissionsFilteredByExistanceOfReviewCommand(Boolean.TRUE) ));
		
		this.menu.addItem(new MenuItem("all submissions", 
				listSubmissionsFilteredByExistanceOfReviewCommand(null) ));
		
		log.leave();
	}

    /**
     * @param exists three valued: may be null
     * @return Command configured to include appropriate records
     */
    private Command listSubmissionsFilteredByExistanceOfReviewCommand(final Boolean exists) { 
    	return new Command() { 
			public void execute() { 
            	listSubmissionsWidget.getModel().setFilterByReviewExistance(exists);
				setActiveChild(listSubmissionsWidget);
				fireEvent(new MenuEvent());
				
			} 
		};
    }
	
	
	@Override
	protected void setActiveChild(Widget child, boolean memorise) {
		super.setActiveChild(child, memorise);

		if (child instanceof ListSubmissionsWidget) {
			ListSubmissionsWidget w = (ListSubmissionsWidget) child;
			w.refresh();
		}
	}


	
	
}
