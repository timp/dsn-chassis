/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.List;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author raok
 *
 */
public class ViewSubmissionDataFilesWidgetDefaultRenderer implements ViewSubmissionDataFilesWidgetModelListener {

	
	
	
	// TODO refactor to use I18N resources
	
	
	
	
	//Expose view elements for testing purposes.
	FlowPanel submissionDataFilesListPanel = new FlowPanel();
	Panel loadingPanel = new SimplePanel();
	
	
	
	
	final private FlowPanel canvas;

	
	
	
	public ViewSubmissionDataFilesWidgetDefaultRenderer() {
		this.canvas = new FlowPanel();

		initCanvas();
	}

	
	
	
	private void initCanvas() {

		canvas.addStyleName(CommonStyles.VIEWSUBMISSIONDATAFILES_BASE);
		
		this.canvas.add(new HTML("<p>The following data files are associated with this submission...</p>"));

		this.loadingPanel.add(new Label("Loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);
		
		this.submissionDataFilesListPanel.setVisible(false);
		this.canvas.add(this.submissionDataFilesListPanel);
		
	}

	
	

	private Widget[] renderRow(AtomEntry in) {

		String title = in.getTitle();
		Label fileTitleLabel = new Label(title);
		fileTitleLabel.addStyleName(CommonStyles.VIEWSUBMISSIONDATAFILES_FILETITLE);
		
		String summary = in.getSummary();
		Label fileSummaryLabel = new Label(RenderUtils.truncate(summary, 100));
		fileSummaryLabel.addStyleName(CommonStyles.VIEWSUBMISSIONDATAFILES_FILESUMMARY);
		
		Label byLabel = new Label(in.getAuthors().get(0).getEmail());

		Label dateLabel = new Label(in.getPublished());
		
		String editMediaLink = ConfigurationBean.getDataFileFeedURL() + "/" + in.getEditMediaLink().getHref();
		HTML downloadFileLink = new HTML("<a href=\"" + editMediaLink + "\" >download</a>" );
		downloadFileLink.addStyleName(CommonStyles.VIEWSUBMISSIONDATAFILES_DOWNLOADLINK);
		
		Widget[] out = { fileTitleLabel, fileSummaryLabel, byLabel, dateLabel, downloadFileLink };
		return out;

	}
	
	
	
	
	
	private Function<AtomEntry, Widget[]> rowGenerator = new Function<AtomEntry, Widget[]>() {
		
		public Widget[] apply(AtomEntry in) {

			return renderRow(in);
			
		}
		
	};
	
	
	
	
	
	public void onDataFileAtomEntriesChanged(List<AtomEntry> before, List<AtomEntry> entries) {
		
		// clear container panel
		submissionDataFilesListPanel.clear();
		
		// define headers
		String[] headers = { "Original File Name", "Comment", "Uploaded By", "Upload Date", "Actions" };
		
		// create header row
		Widget[] headerRow = RenderUtils.renderLabels(headers, CommonStyles.VIEWSUBMISSIONDATAFILES_TABLEHEADER);
		
		// create rows
		List<Widget[]> rows = RenderUtils.renderAsRows(entries, this.rowGenerator);
		
		// insert header row at the top
		rows.add(0, headerRow);
		
		// render flex table from rows
		FlexTable dataFilesTable = RenderUtils.renderFlexTable(rows);
		
		// tweak table style
		dataFilesTable.setCellPadding(0);
		dataFilesTable.setCellSpacing(0);
		dataFilesTable.addStyleName(CommonStyles.VIEWSUBMISSIONDATAFILES_DATAFILESTABLE);
		
		// add table to container panel
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
