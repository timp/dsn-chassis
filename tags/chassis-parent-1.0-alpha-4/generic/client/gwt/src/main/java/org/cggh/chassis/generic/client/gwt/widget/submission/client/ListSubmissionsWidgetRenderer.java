/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

/**
 * @author aliman
 *
 */
public class ListSubmissionsWidgetRenderer 
     extends AsyncWidgetRenderer<ListSubmissionsWidgetModel> {

	Log log = LogFactory.getLog(ListSubmissionsWidgetRenderer.class);

	
	
	
	private FlowPanel resultsTableContainer;
	private ListSubmissionsWidget owner;


	
	
	public ListSubmissionsWidgetRenderer(ListSubmissionsWidget owner) {
		this.owner = owner;
	}
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		
		this.resultsTableContainer = new FlowPanel();
		this.mainPanel.add(this.resultsTableContainer);

	}
	
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		this.model.addSubmissionFeedChangeHandler(new SubmissionFeedChangeHandler() {
			
			public void onChange(SubmissionFeedChangeEvent e) {
				
				resultsTableContainer.clear();
				if (e.getAfter() != null)
					syncUIWithSubmissionFeed(e.getAfter());
				
			}
		});
		
	}




	
	protected void syncUIWithSubmissionFeed(SubmissionFeed feed) {
		
		String[] headers;
		
		if (model.getFilterByReviewExistance() == null) { 
			this.resultsTableContainer.add(h2Widget("All Submissions"));
			String[] allHeaders = {
					"Dataset Title",
					"Dataset Summary",
					"Submitter",
					"Submission Date",
					"Reviewer",
					"Review Date",
					// TODO add curator					
//					"Curator",
					"Actions"
			};
			headers = allHeaders;
		} else if (model.getFilterByReviewExistance().booleanValue()) { 
			this.resultsTableContainer.add(h2Widget("Accepted Submissions"));
			this.resultsTableContainer.add(pWidget("The following submissions have been reviewed by a gatekeeper and accepted..."));			
			String[] acceptedHeaders = {
					"Dataset Title",
					"Dataset Summary",
					"Submitter",
					"Submission Date",
					"Reviewer",
					"Review Date",
					// TODO add curator					
//					"Curator",
					"Actions"
			};
			headers = acceptedHeaders;
		} else { 
			this.resultsTableContainer.add(h2Widget("Submissions Pending Review"));
			this.resultsTableContainer.add(pWidget("The following submissions have not been reviewed by a gatekeeper..."));
			String[] pendingHeaders = {
					"Dataset Title",
					"Dataset Summary",
					"Submitter",
					"Submission Date",
					"Actions"
			};
			headers = pendingHeaders;
		}

   		
			
		Widget[] headerRow = RenderUtils.renderLabels(headers);
			
		List<Widget[]> rows = new ArrayList<Widget[]>();
		rows.add(headerRow);
			
		for (SubmissionEntry entry : feed.getEntries()) {
			if (model.getFilterByReviewExistance() == null) {
				rows.add(this.renderRow(entry));
			} else if (model.getFilterByReviewExistance().booleanValue()) { 
				if (entry.getReviewLink() != null)
					rows.add(this.renderRow(entry));
			} else { 
				if (entry.getReviewLink() == null)
					rows.add(this.renderRow(entry));
			}
			
		}
			
		FlexTable table = RenderUtils.renderResultsTable(rows);
		this.resultsTableContainer.add(table);
			
		
	}



	protected Widget[] renderRow(final SubmissionEntry submissionEntry) {

		DatasetEntry datasetEntry = submissionEntry.getDatasetLink().getEntry();
		
		Widget datasetTitle = new Label(datasetEntry.getTitle());
		Widget datasetSummary = new Label(RenderUtils.truncate(datasetEntry.getSummary(), 40));
		Widget submitter = RenderUtils.renderAtomAuthorsAsLabel(submissionEntry.getAuthors(), false);
		Widget submissionDate = new Label(submissionEntry.getPublished());
		
		
		
		Anchor viewAction = RenderUtils.renderActionAsAnchor("view", new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				log.enter("renderRow.renderActionAsAnchor.ClickHandler");

				ViewSubmissionActionEvent e = new ViewSubmissionActionEvent();
				log.debug("Submissionentry:" + submissionEntry.getId());
				e.setEntry(submissionEntry);
				owner.fireEvent(e);
				
				log.leave();
			}
		});
		
		Widget[] row;
		if (model.getFilterByReviewExistance() != null && !model.getFilterByReviewExistance().booleanValue()) { 
			Widget[] pendingRow = {
				datasetTitle,
				datasetSummary,
				submitter,
				submissionDate,
				viewAction
			};
			row = pendingRow;
		} else { 
			Widget reviewers, reviewDate;
			if(submissionEntry.getReviewLink() != null) { 
				reviewers = RenderUtils.renderAtomAuthorsAsLabel(submissionEntry.getReviewLink().getEntry().getAuthors(), false);
				reviewDate = new Label(submissionEntry.getReviewLink().getEntry().getPublished());
			} else { 
				reviewers = RenderUtils.renderAtomAuthorsAsLabel((Collection<AtomAuthor>)null, false);
				reviewDate = new InlineLabel("");
			}
			Widget[] allRow = {
				datasetTitle,
				datasetSummary,
				submitter,
				submissionDate,
				reviewers,
				reviewDate,
				viewAction
			};
			row = allRow;
		}
		
		
		return row;
	}

	
	
	
}
