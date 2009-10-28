/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;

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
public class ViewSubmissionDataFilesWidgetDefaultRenderer implements ViewSubmissionDataFilesWidgetModelListener {

	//Expose view elements for testing purposes.
	FlowPanel submissionDataFilesListPanel = new FlowPanel();
	Panel loadingPanel = new SimplePanel();
	
	private ViewSubmissionDataFilesWidgetController controller;
	final private FlowPanel canvas;

	public ViewSubmissionDataFilesWidgetDefaultRenderer(ViewSubmissionDataFilesWidgetController controller) {
		this.canvas = new FlowPanel();
		this.controller = controller;

		initCanvas();
	}

	private void initCanvas() {

		canvas.addStyleName(CSS.VIEWSUBMISSIONDATAFILES_BASE);
		
		this.canvas.add(new HTML("<p>Listed below is a revision list of the data file associated with this submission.</p>"));

		this.loadingPanel.add(new Label("Loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);
		
		this.submissionDataFilesListPanel.setVisible(false);
		this.canvas.add(this.submissionDataFilesListPanel);
		
	}

	public void onDataFileAtomEntriesChanged(List<AtomEntry> before, List<AtomEntry> after) {
		
		submissionDataFilesListPanel.clear();
		
		//create file revision list
		//TODO order by date?
		
		FlexTable dataFilesTable = new FlexTable();
		dataFilesTable.setCellPadding(0);
		dataFilesTable.setCellSpacing(0);
		dataFilesTable.addStyleName(CSS.VIEWSUBMISSIONDATAFILES_DATAFILESTABLE);
		int rowNo = 0;
		
		//headers
		String[] headers = { "Title", "Summary", ""};
		
		for (int i=0; i<headers.length; i++) {
			Label headerLabel = new Label(headers[i]);
			headerLabel.addStyleName(CSS.VIEWSUBMISSIONDATAFILES_TABLEHEADER);
			dataFilesTable.setWidget(rowNo, i, headerLabel);
		}
		
		for (AtomEntry dataFileEntry : after) {
			
			String title = dataFileEntry.getTitle();
			Label dataFileTitle = new Label(title);
			dataFileTitle.addStyleName(CSS.VIEWSUBMISSIONDATAFILES_FILETITLE);
			dataFilesTable.setWidget(++rowNo, 0, dataFileTitle);
			
			String summary = dataFileEntry.getSummary();
			int cutoff = 100;
			if (summary.length() > cutoff) {
				summary = summary.substring(0, cutoff) + "...";
			}
			Label fileSummary = new Label(summary);
			fileSummary.addStyleName(CSS.VIEWSUBMISSIONDATAFILES_FILESUMMARY);
			dataFilesTable.setWidget(rowNo, 1, fileSummary);
			
			String editMediaLink = ConfigurationBean.getDataFileFeedURL() + "/" + dataFileEntry.getEditMediaLink().getHref();
			HTML downloadFileLink = new HTML("<a href=\"" + editMediaLink + "\" >download</a>" );
			downloadFileLink.addStyleName(CSS.VIEWSUBMISSIONDATAFILES_DOWNLOADLINK);
			dataFilesTable.setWidget(rowNo, 2, downloadFileLink);
			
		}
		
		submissionDataFilesListPanel.add(dataFilesTable);
	}

	public void onStatusChanged(Integer before, Integer after) {

		if (after == ViewSubmissionDataFilesWidgetModel.STATUS_LOADING) {
			submissionDataFilesListPanel.setVisible(false);
			loadingPanel.setVisible(true);
		} else if (after == ViewSubmissionDataFilesWidgetModel.STATUS_LOADED) {
			loadingPanel.setVisible(false);
			submissionDataFilesListPanel.setVisible(true);
		}

	}

	public Panel getCanvas() {
		return canvas;
	}

}
