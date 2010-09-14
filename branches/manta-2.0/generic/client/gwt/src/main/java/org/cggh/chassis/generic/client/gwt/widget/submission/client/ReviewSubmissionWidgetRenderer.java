package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextArea;

import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

public class ReviewSubmissionWidgetRenderer 
    extends AsyncWidgetRenderer<ReviewSubmissionWidgetModel>  {

	
	Log log = LogFactory.getLog(ReviewSubmissionWidgetRenderer.class);

	private ReviewSubmissionWidget owner; 
	
	
	private ReviewSubmissionWidgetController controller;

	private SubmissionPropertiesWidget submissionPropertiesWidget; 
	private TextArea commentTextArea;
	private Button acceptButton;
	private Button cancelButton;

	private FlowPanel reviewPanel;
	private FlowPanel reviewedPanel;


	public ReviewSubmissionWidgetRenderer(ReviewSubmissionWidget owner) {
		super();
		this.owner = owner;
	}


	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		
		this.mainPanel.add(h2Widget("Review Submission")); //TODO i18n
		this.submissionPropertiesWidget = new SubmissionPropertiesWidget();
		this.mainPanel.add(this.submissionPropertiesWidget);

		
		this.reviewPanel = new FlowPanel();
		this.reviewPanel.add(h3Widget("Acceptance Review")); //TODO i18n
		
		this.reviewPanel.add(pWidget("Click the \"Accept Submission\" button below to assert that the submission " + 
				"meets all acceptance criteria and will be curated."));
		this.reviewPanel.add(pWidget("Click the \"Cancel\" button if you do not wish to accept the submission at this time."));
		
		FlowPanel buttonsPanel = new FlowPanel();
		
		this.acceptButton = new Button("Accept Submission"); //TODO i18n
		this.cancelButton = new Button("Cancel"); //TODO i18n
		
		buttonsPanel.add(acceptButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.setVisible(true);
		
		this.reviewPanel.add(buttonsPanel);

		this.reviewPanel.add(pWidget("Use the text area below to enter any comments about the submission." + 
				" (N.B., these comments will only be saved if the submission is accepted.)"));
		
		this.commentTextArea = new TextArea();
		this.reviewPanel.add(this.commentTextArea);

		this.mainPanel.add(this.reviewPanel);
		
		this.reviewedPanel = new FlowPanel();
		this.reviewedPanel.add(h3Widget("Reviewed"));
		this.reviewedPanel.add(pWidget("This submission has been reviewed.")); // TODO i18n

		// TODO Review ... this action actually does nothing, cf ShareDatasetWidgetRenderer.renderDatasetAlreadySharedPanel
		//Anchor viewSubmissionAction = RenderUtils.renderActionAnchor("back to view submission..."); // TODO i18n
		//this.reviewedPanel.add(viewSubmissionAction);
		this.mainPanel.add(this.reviewedPanel);
		
		log.leave();
	}

	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		HandlerRegistration b = this.model.addSubmissionEntryChangeHandler(new SubmissionEntryChangeHandler() {
			public void onChange(SubmissionEntryChangeEvent e) {

				syncUIWithSubmissionEntry(e.getAfter());
				
			}
		});

		this.modelChangeHandlerRegistrations.add(b);
		
		this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
			
		});

	}


	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = this.acceptButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				log.enter("clickHandler.onClick");
				
				controller.acceptSubmission(commentTextArea.getText());
			
				log.leave();
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		
		HandlerRegistration b = this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				owner.fireEvent(new CancelEvent());
			}
			
		});

		this.childWidgetEventHandlerRegistrations.add(b);
		
	}
	

	@Override
	protected void syncUI() {
		log.enter("syncUI");

		super.syncUI();
		
		this.syncUIWithSubmissionEntry(this.model.getSubmissionEntry());
		
		
		log.leave();
	}

	
	
	/**
	 * @param entry
	 */
	protected void syncUIWithSubmissionEntry(SubmissionEntry entry) {
		log.enter("syncUIWithSubmissionEntry");

		submissionPropertiesWidget.setEntry(entry);
		this.commentTextArea.setValue("");

		syncUIWithStatus(this.model.getStatus());
		
		log.leave();
	}




	private void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			
			log.debug("initial");
			this.reviewPanel.setVisible(false);
			this.reviewedPanel.setVisible(false);
			
		}
		else if (status instanceof ReviewSubmissionWidgetModel.SubmissionRetrievedStatus) {

			log.debug("has submission already been reviewed?");
			
			boolean alreadyReviewed = (this.model.getSubmissionEntry().getReviewLink() != null);
			
			if (alreadyReviewed) {

				log.debug("already reviewed");
				
				this.reviewPanel.setVisible(false);
				this.reviewedPanel.setVisible(true);
				
			}
			else {
			
				log.debug("not yet reviewed");
				
				this.reviewPanel.setVisible(true);
				this.reviewedPanel.setVisible(false);
				
			}
			
		}
		else if (status instanceof ReviewSubmissionWidgetModel.ReviewCreatedStatus) {
			
			this.reviewPanel.setVisible(false);
			this.reviewedPanel.setVisible(true);

			log.debug("Review created");
			
		}
		else if (status instanceof ReviewSubmissionWidgetModel.RetrieveSubmissionPendingStatus) {
			
			this.reviewPanel.setVisible(false);
			this.reviewedPanel.setVisible(true);

			log.debug("RetrieveSubmissionPendingStatus");
			
		}
		else { 
			// TODO fail hard
			log.debug("Uncatered for status " + status.getClass().getName() );
		}
		log.leave();
		
	}


	public void setController(ReviewSubmissionWidgetController controller) {
		this.controller = controller;
	}


}
