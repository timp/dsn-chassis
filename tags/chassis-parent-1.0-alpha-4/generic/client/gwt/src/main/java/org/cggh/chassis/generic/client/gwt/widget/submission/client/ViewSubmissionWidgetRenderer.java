/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.review.ReviewEntry;
import org.cggh.chassis.generic.atomext.client.review.ReviewLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import static org.cggh.chassis.generic.widget.client.HtmlElements.*;




/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetRenderer extends
		AsyncWidgetRenderer<AtomCrudWidgetModel<SubmissionEntry>> {

	
	
	
	public class BubbleSubmissionEventHandler implements
			SubmissionActionHandler {

		public void onAction(SubmissionActionEvent e) {

			// augment event and bubble
			e.setEntry(model.getEntry());
			owner.fireEvent(e);

		}

	}



	private Log log = LogFactory.getLog(ViewSubmissionWidgetRenderer.class);
	private ViewSubmissionWidget owner;
	
	
	
	// UI fields
	private SubmissionPropertiesWidget submissionPropertiesWidget;
	private SubmissionActionsWidget actionsWidget;
	private FlowPanel acceptanceReviewContainer;
	private ViewDatasetWidget viewDatasetWidget;




	
	
	
	public ViewSubmissionWidgetRenderer(ViewSubmissionWidget owner) {
		this.owner = owner;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		
		this.mainPanel.add(h2Widget("View Submission"));
		
		this.submissionPropertiesWidget = new SubmissionPropertiesWidget();
		this.mainPanel.add(this.submissionPropertiesWidget);

		this.mainPanel.add(h3Widget("Acceptance Review"));
		this.acceptanceReviewContainer = new FlowPanel();
		this.mainPanel.add(this.acceptanceReviewContainer);
		
		this.mainPanel.add(h3Widget("Submitted Dataset"));
		this.viewDatasetWidget = new CustomViewDatasetWidget();
		this.mainPanel.add(this.viewDatasetWidget);
		
		// TODO review this style name
		this.viewDatasetWidget.addStyleName("xquestionnaire");
		
		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);

		this.renderActionsWidget();
		log.leave();
	}
	
	protected void renderActionsWidget() {
		log.enter("renderActionsWidget");
		this.actionsWidget = new SubmissionActionsWidget();

		this.mainPanel.add(this.actionsWidget);		

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

	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = this.viewDatasetWidget.addViewDataFileActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {
				// just bubble
				owner.fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		
		HandlerRegistration b = this.viewDatasetWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				// just bubble
				owner.fireEvent(e);
			}
		});

		this.childWidgetEventHandlerRegistrations.add(b);
		
		if (this.actionsWidget != null ) {
			
			HandlerRegistration aa = this.actionsWidget.addReviewSubmissionActionHandler(new BubbleSubmissionEventHandler());
			this.childWidgetEventHandlerRegistrations.add(aa);
			
			HandlerRegistration ab = this.actionsWidget.addAssignCuratorActionHandler(new BubbleSubmissionEventHandler());
			this.childWidgetEventHandlerRegistrations.add(ab);
		}
		
        log.leave();
	}


	
	
	/**
	 * @param entry
	 */
	protected void syncUIWithSubmissionEntry(SubmissionEntry entry) {
		log.enter("syncUIWithSubmissionEntry");
		
		this.submissionPropertiesWidget.setEntry(entry);
		
		this.syncAcceptanceReviewSection(entry);

		if (entry != null) // all submissions must have a dataset so no need to check dataset not null
			this.viewDatasetWidget.setEntry(entry.getDatasetLink().getEntry());
		
		if (model != null && model.getEntry() != null && model.getEntry().getReviewLink() != null) { 
			this.actionsWidget.getReviewSubmissionAction().setVisible(false); 
			this.actionsWidget.getAssignCuratorAction().setVisible(true);
		} else { 
			this.actionsWidget.getReviewSubmissionAction().setVisible(true); 
			this.actionsWidget.getAssignCuratorAction().setVisible(false);
			
		}
		log.leave();
	}



	/**
	 * @param submissionEntry
	 */
	private void syncAcceptanceReviewSection(final SubmissionEntry submissionEntry) {
		log.enter("syncAcceptanceReviewSection");
		
		this.acceptanceReviewContainer.clear();
		List<ReviewLink> reviewLinks = null;
		
		if (submissionEntry != null) { 
			reviewLinks = submissionEntry.getReviewLinks();
			log.debug("Reviews found: " + reviewLinks.size());
		} else
			log.debug("Submission null");
		if (reviewLinks != null && reviewLinks.size() > 0) {
			
			String[] headers = {
					"Reviewer",
					"Date",
					"Summary"
			};
			Widget[] headerRow = RenderUtils.renderLabels(headers);
			List<Widget[]> rows = new ArrayList<Widget[]>();
			rows.add(headerRow);
			
			
			for (ReviewLink link : reviewLinks) { 
				
				rows.add(renderReviewAsRow(link.getEntry()));
					
			}
			FlexTable table = RenderUtils.renderResultsTable(rows);
			this.acceptanceReviewContainer.add(table);
			
		}
		else {

			this.acceptanceReviewContainer.add(pWidget("This submission is pending review."));
			
			Anchor reviewSubmissionAction = RenderUtils.renderActionAnchor("review this submission...");
			
			reviewSubmissionAction.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					log.enter("onClick");
					ReviewSubmissionActionEvent e = new ReviewSubmissionActionEvent();
					e.setEntry(submissionEntry);
					owner.fireEvent(e);
					log.leave();
				}
			}
			);
			
			this.acceptanceReviewContainer.add(reviewSubmissionAction);
		}

		
		log.leave();
	}

	
	protected Widget[] renderReviewAsRow(final ReviewEntry entry) {

		Widget[] row = {
				RenderUtils.renderAtomAuthorsAsLabel(entry, false),
				emWidget(entry.getPublished()),	
				new Label(RenderUtils.truncate(entry.getSummary(), 50))
		};
		
		return row;
	}

	
}
