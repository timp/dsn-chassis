package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

	private FlowPanel reviewCommentPanel;


	public ReviewSubmissionWidgetRenderer(ReviewSubmissionWidget owner) {
		super();
		this.owner = owner;
	}


	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		this.mainPanel.add(h2Widget("Review submission")); //TODO i18n
		this.submissionPropertiesWidget = new SubmissionPropertiesWidget();
		this.mainPanel.add(this.submissionPropertiesWidget);

		
		this.reviewCommentPanel = new FlowPanel();
		this.reviewCommentPanel.add(h3Widget("Review")); //TODO i18n
		
		this.commentTextArea = new TextArea();
		this.reviewCommentPanel.add(this.commentTextArea);

		
		FlowPanel buttonsPanel = new FlowPanel();
		
		this.acceptButton = new Button("Accept Submission into "+Configuration.getNetworkName()); //TODO i18n
		this.cancelButton = new Button("Cancel"); //TODO i18n
		
		buttonsPanel.add(acceptButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.setVisible(true);
		
		this.reviewCommentPanel.add(buttonsPanel);

		

		this.mainPanel.add(this.reviewCommentPanel);
		
		log.leave();
	}

	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		this.model.addChangeHandler(new SubmissionEntryChangeHandler() {
				public void onChange(SubmissionEntryChangeEvent e) {
					log.enter("SubmissionEntryChangeEvent.onChange");

					
					if (e.getAfter() != null)
						submissionPropertiesWidget.setEntry(e.getAfter());
					
					
					log.leave();
				}

			}, SubmissionEntryChangeEvent.TYPE);
	}


	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		this.acceptButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				log.enter("clickHandler.onClick");
				
				
				controller.acceptSubmission(commentTextArea.getText());
			
				
				log.leave();
			}
			
		});
		
		this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				owner.fireEvent(new CancelEvent());
			}
			
		});
		
		/*
		 * We only have one action 
		 * 
		for (HasClickHandlers action : this.viewDatasetActions) {
		
			action.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					owner.fireViewDatasetActionEvent();
				}
				
			});
		}
		*/
		
	}
	

	@Override
	protected void syncUI() {
		log.enter("syncUI");

		super.syncUI();
		
		this.submissionPropertiesWidget.setEntry(this.model.getSubmissionEntry());
		
		this.commentTextArea.setValue("");

		synchUIWithStatus(this.model.getStatus());
		
		log.leave();
	}


	private void synchUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			
			log.debug("initial");
			this.reviewCommentPanel.setVisible(true);
			
		}
		else if (status instanceof ReviewSubmissionWidgetModel.SubmissionRetrievedStatus) {

			log.debug("has submission already been reviewed?");
			
			boolean alreadyReviewed = (this.model.getSubmissionEntry().getReviewLinks() != null);
			
			if (alreadyReviewed) {

				log.debug("already reviewed");
				
				this.reviewCommentPanel.setVisible(false);
				
			}
			else {
			
				log.debug("not yet reviewed");
				
				this.reviewCommentPanel.setVisible(true);
				
			}
			
		}
		else if (status instanceof ReviewSubmissionWidgetModel.ReviewCreatedStatus) {
			
			this.reviewCommentPanel.setVisible(false);

			log.debug("Review created");
			// TODO Set created panel status
			
		}
		else { 
			log.debug("Uncatered for status " + status.getClass().getName() );
		}
		log.leave();
		
	}


	public void setController(ReviewSubmissionWidgetController controller) {
		this.controller = controller;
	}


}
