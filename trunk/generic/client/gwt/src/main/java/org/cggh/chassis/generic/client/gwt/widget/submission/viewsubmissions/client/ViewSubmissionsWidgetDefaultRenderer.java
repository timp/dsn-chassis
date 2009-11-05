/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.atom.rewrite.client.AtomAuthor;
import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;




/**
 * @author raok
 *
 */
public class ViewSubmissionsWidgetDefaultRenderer implements ViewSubmissionsWidgetModelListener {

	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	//Expose view elements for testing purposes.
	Panel submissionsListPanel = new SimplePanel();
	Panel loadingPanel = new SimplePanel();
	
	private Panel canvas;
	private ViewSubmissionsWidgetController controller;

	
	
	
	
	/**
	 * 
	 * @param canvas
	 * @param controller
	 */
	public ViewSubmissionsWidgetDefaultRenderer(Panel canvas, ViewSubmissionsWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		initCanvas();
	}

	
	
	
	
	/**
	 * @param controller
	 */
	public ViewSubmissionsWidgetDefaultRenderer(ViewSubmissionsWidgetController controller) {
		this.canvas = new FlowPanel();
		this.controller = controller;
		
		initCanvas();
	}

	
	
	
	
	private void initCanvas() {
		log.enter("initCanvas");
		
		this.canvas.addStyleName(CommonStyles.VIEWSUBMISSIONS_BASE);
		
		this.canvas.add(new HTML("<h2>My Data Submissions</h2>"));
		
		this.canvas.add(new HTML("<p>Listed below are all of the data submissions that you own.</p>"));
		
		log.debug("prepare loading panel");
		this.loadingPanel.add(new Label("Loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);
		
		log.debug("prepare studies list panel");
		this.submissionsListPanel.setVisible(false);
		this.canvas.add(this.submissionsListPanel);
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetModelListener#onStatusChanged(java.lang.Integer, java.lang.Integer)
	 */
	public void onStatusChanged(Integer before, Integer after) {

		if (after == ViewSubmissionsWidgetModel.STATUS_LOADING) {

			this.submissionsListPanel.setVisible(false);
			this.loadingPanel.setVisible(true);
			
		} else if (after == ViewSubmissionsWidgetModel.STATUS_LOADED) {

			this.loadingPanel.setVisible(false);
			this.submissionsListPanel.setVisible(true);

		}
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetModelListener#onSubmissionEntriesChanged(java.util.List, java.util.List)
	 */
	public void onSubmissionEntriesChanged(List<SubmissionEntry> before, List<SubmissionEntry> after) {
		
		log.enter("onSubmissionEntriesChanged");
		
		log.debug("remove old entries");

		submissionsListPanel.clear();
		
		log.debug("create table to list submissions in");

		FlexTable submissionsTable = new FlexTable();
		submissionsTable.setCellPadding(0);
		submissionsTable.setCellSpacing(0);
		submissionsTable.addStyleName(CommonStyles.VIEWSUBMISSIONS_SUBMISSIONSTABLE);
		int rowNo = 0;		
		
		log.debug("add header row");

//		String[] headers = { "Title", "Summary", "Modules", "Owners", "Created", "Updated", "Actions" };
		String[] headers = { "Title", "Summary", "Modules", "Owners", "Actions" };
		
		for (int i=0; i<headers.length; i++) {
			Label headerLabel = new Label(headers[i]);
			headerLabel.addStyleName(CommonStyles.VIEWSUBMISSIONS_TABLEHEADER);
			submissionsTable.setWidget(rowNo, i, headerLabel);
		}
		
		log.debug("add submissions");
		
		for (SubmissionEntry submissionEntry : after) {

			String title = submissionEntry.getTitle();
			
			String summary = submissionEntry.getSummary();
			int cutoff = 100;
			if (summary != null && summary.length() > cutoff) {
				log.debug("truncate long summary");
				summary = summary.substring(0, cutoff) + "...";
			}
			
			List<String> modules = submissionEntry.getModules();
			Map<String,String> moduleLabels = ConfigurationBean.getModules();
			String modulesContent = "";
			for (Iterator<String> it = modules.iterator(); it.hasNext(); ) {
				String ml = moduleLabels.get(it.next());
				modulesContent += ml;
				if (it.hasNext()) {
					modulesContent += ", ";
				}
			}
			
			String created = submissionEntry.getPublished();
			String updated = submissionEntry.getUpdated();
			
			Label submissionTitleLabel = new Label(title);
			submissionTitleLabel.addStyleName(CommonStyles.VIEWSUBMISSIONS_SUBMISSIONTITLE);
			submissionsTable.setWidget(++rowNo, 0, submissionTitleLabel);
			
			submissionsTable.setWidget(rowNo, 1, new Label(summary));
			submissionsTable.setWidget(rowNo, 2, new Label(modulesContent));
			
			String authorsContent = "";
			for (Iterator<AtomAuthor> it = submissionEntry.getAuthors().iterator(); it.hasNext(); ) {
				authorsContent += it.next().getEmail();
				if (it.hasNext()) {
					authorsContent += ", ";
				}
			}
			
			submissionsTable.setWidget(rowNo, 3, new Label(authorsContent));

//			submissionsTable.setWidget(rowNo, 4, new Label(created));
//			submissionsTable.setWidget(rowNo, 5, new Label(updated));
			
			log.debug("add a select submission link");
			Anchor selectSubmission = new Anchor();
			selectSubmission.setText("view");
			selectSubmission.addStyleName(CommonStyles.COMMON_ACTION);
			selectSubmission.addClickHandler(new SelectSubmissionClickHandler(submissionEntry));
			submissionsTable.setWidget(rowNo, 4, selectSubmission);
						
		}
		
		submissionsListPanel.add(submissionsTable);
		
		log.leave();
	
	}
	
	
	
	
	//package private to allow testing
	class SelectSubmissionClickHandler implements ClickHandler {
		
		private final SubmissionEntry submissionEntry;
		
		public SelectSubmissionClickHandler(SubmissionEntry submissionEntry) {
			this.submissionEntry = submissionEntry;
		}
		
		public void onClick(ClickEvent arg0) {
			controller.onUserSelectSubmission(submissionEntry);
		}
	}

	
	
	
	public void setController(ViewSubmissionsWidgetController controller) {
		this.controller = controller;
	}

	
	
	
	/**
	 * @return
	 */
	public Panel getCanvas() {
		return this.canvas;
	}
	
	
	

}
