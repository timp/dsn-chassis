/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.List;

import org.cggh.chassis.generic.atomext.client.review.ReviewLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

import static org.cggh.chassis.generic.widget.client.HtmlElements.*;




/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetRenderer extends
		AsyncWidgetRenderer<AtomCrudWidgetModel<SubmissionEntry>> {

	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidgetRenderer.class);
	
	
	
	// UI fields
	private SubmissionPropertiesWidget submissionPropertiesWidget;
	private FlowPanel acceptanceReviewContainer;
	private ViewDatasetWidget viewDatasetWidget;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		
		this.mainPanel.add(h2("View Submission"));
		
		this.submissionPropertiesWidget = new SubmissionPropertiesWidget();
		this.mainPanel.add(this.submissionPropertiesWidget);

		this.mainPanel.add(h3("Acceptance Review"));
		this.acceptanceReviewContainer = new FlowPanel();
		this.mainPanel.add(this.acceptanceReviewContainer);
		
		this.mainPanel.add(h3("Submitted Dataset"));
		this.viewDatasetWidget = new CustomViewDatasetWidget();
		this.mainPanel.add(this.viewDatasetWidget);
		
		// TODO review this style name
		this.viewDatasetWidget.addStyleName("xquestionnaire");
		
		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);

		log.leave();
	}
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		this.model.addEntryChangeHandler(new AtomEntryChangeHandler<SubmissionEntry>() {
			
			public void onEntryChanged(AtomEntryChangeEvent<SubmissionEntry> e) {
				syncUIWithSubmissionEntry(e.getAfter());
			}
		});
		
	}



	
	
	/**
	 * @param entry
	 */
	protected void syncUIWithSubmissionEntry(SubmissionEntry entry) {
		log.enter("syncUIWithSubmissionEntry");
		
		this.submissionPropertiesWidget.setEntry(entry);
		
		this.syncAcceptanceReviewSection(entry);
		
		this.viewDatasetWidget.setEntry(entry.getDatasetLink().getEntry());
		
		log.leave();
	}



	/**
	 * @param entry
	 */
	private void syncAcceptanceReviewSection(SubmissionEntry entry) {
		log.enter("syncAcceptanceReviewSection");
		
		List<ReviewLink> reviewLinks = entry.getReviewLinks();
		
		if (reviewLinks.size() > 0) {
			
			// TODO
			
		}
		else {

			this.acceptanceReviewContainer.add(p("This submission is pending review."));
			
			Anchor reviewSubmissionAction = RenderUtils.renderActionAnchor("review this submission...");
			
			reviewSubmissionAction.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					log.enter("onClick");
					
					// TODO fire new review submission action event
					
					log.leave();
					
				}
			});
			
			this.acceptanceReviewContainer.add(reviewSubmissionAction);
			
		}

		
		log.leave();
	}

	
	
	
}
