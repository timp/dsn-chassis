/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class ViewSubmissionsPendingReviewWidgetRenderer extends
		AsyncWidgetRenderer<ViewSubmissionsPendingReviewWidgetModel> {

	
	
	
	private FlowPanel resultsTableContainer;




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {

		this.mainPanel.add(h2("View Submissions Pending Review"));
		
		this.mainPanel.add(p("The following submissions have not been reviewed by a gatekeeper..."));
		
		this.resultsTableContainer = new FlowPanel();
		this.mainPanel.add(this.resultsTableContainer);

	}
	
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		this.model.addSubmissionFeedChangeHandler(new SubmissionFeedChangeHandler() {
			
			public void onChange(SubmissionFeedChangeEvent e) {
				
				syncUIWithSubmissionFeed(e.getAfter());
				
			}
		});
		
	}




	/**
	 * @param after
	 */
	protected void syncUIWithSubmissionFeed(SubmissionFeed feed) {
		
		this.resultsTableContainer.clear();
		
		if (feed != null) {
			
			String[] headers = {
					"Dataset Title",
					"Dataset Summary",
					"Submitter",
					"Submission Date",
					"Actions"
			};
			
			Widget[] headerRow = RenderUtils.renderLabels(headers);
			
			List<Widget[]> rows = new ArrayList<Widget[]>();
			rows.add(headerRow);
			
			for (SubmissionEntry entry : feed.getEntries()) {
				
				Widget[] row = this.renderRow(entry);
				rows.add(row);
				
			}
			
			FlexTable table = RenderUtils.renderResultsTable(rows);
			this.resultsTableContainer.add(table);
			
		}
		
	}




	/**
	 * @param entry
	 * @return
	 */
	private Widget[] renderRow(SubmissionEntry submissionEntry) {

		DatasetEntry datasetEntry = submissionEntry.getDatasetLink().getEntry();
		
		Widget datasetTitle = new Label(datasetEntry.getTitle());
		Widget datasetSummary = new Label(RenderUtils.truncate(datasetEntry.getSummary(), 40));
		Widget submitter = RenderUtils.renderAtomAuthorsAsLabel(submissionEntry.getAuthors(), false);
		Widget submissionDate = new Label(submissionEntry.getPublished());
		
		Anchor viewAction = RenderUtils.renderActionAsAnchor("view", new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {

				// TODO Auto-generated method stub
				
			}
		});
		
		Widget[] row = {
			datasetTitle,
			datasetSummary,
			submitter,
			submissionDate,
			viewAction
		};
		
		return row;
	}

	
	
	
}
