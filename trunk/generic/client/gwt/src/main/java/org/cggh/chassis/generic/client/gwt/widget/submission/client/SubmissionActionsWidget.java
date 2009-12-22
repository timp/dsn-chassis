package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/** 
 * @author timp 
 * @since 04/12/2009
 */
public class SubmissionActionsWidget extends ChassisWidget {

	private Log log = LogFactory.getLog(SubmissionActionsWidget.class);
	private Anchor reviewSubmissionAction, assignCuratorAction;

	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.clear();
		
		this.reviewSubmissionAction =  RenderUtils.renderActionAnchor("review submission"); // TODO i18n
		this.assignCuratorAction =  RenderUtils.renderActionAnchor("assign curator"); // TODO i18n
		
		Widget[] actions = { 
				this.reviewSubmissionAction,
				this.assignCuratorAction
		};
		
		FlowPanel actionsPanel = RenderUtils.renderActionsPanel(actions);

		this.add(actionsPanel);
		
		log.leave();
	}
	
	

	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		HandlerRegistration a = this.reviewSubmissionAction.addClickHandler(new ReviewActionClickHandler());

		// store registrations so we can remove handlers later if necessary
		this.childWidgetEventHandlerRegistrations.add(a);
		
		HandlerRegistration b = this.assignCuratorAction.addClickHandler(new AssignCuratorClickHandler());
		
		this.childWidgetEventHandlerRegistrations.add(b);
		
		log.leave();
	}


    

    private class ReviewActionClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			// map click event to action event and fire
			log.enter("onclick");
			fireEvent(new ReviewSubmissionActionEvent());
			log.leave();

		}

	}

    
    
    
	private class AssignCuratorClickHandler implements ClickHandler {

		private Log log = LogFactory.getLog(AssignCuratorClickHandler.class);

		public void onClick(ClickEvent arg0) {
			log.enter("onClick");
			
			log.debug("map click event to assign curator action event and fire");
			fireEvent(new AssignCuratorActionEvent());
			
			log.leave();
		}

	}







	public HandlerRegistration addReviewSubmissionActionHandler(
			SubmissionActionHandler h) {
		
		log.enter("addReviewSubmissionActionHandler");

		HandlerRegistration hr = this.addHandler(h, ReviewSubmissionActionEvent.TYPE);

		log.leave();
		return hr;
	}
	
	
	
	public HandlerRegistration addAssignCuratorActionHandler(
		SubmissionActionHandler h
	) {
		
		HandlerRegistration hr = this.addHandler(h, AssignCuratorActionEvent.TYPE);
		
		return hr;
		
	}



	public Anchor getReviewSubmissionAction() {
		return reviewSubmissionAction;
	}



	public Anchor getAssignCuratorAction() {
		return assignCuratorAction;
	}



}
