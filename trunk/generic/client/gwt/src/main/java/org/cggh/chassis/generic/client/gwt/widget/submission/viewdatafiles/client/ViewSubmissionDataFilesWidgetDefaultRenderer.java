/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;

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
		
		this.canvas.add(new HTML("<h2>Data File Revisions</h2>"));
		
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
		int rowNo = 0;
		
		//headers
		dataFilesTable.setWidget(rowNo, 0, new Label("File Name"));
		dataFilesTable.setWidget(rowNo, 1, new Label("Summary"));
		
		for (AtomEntry dataFileEntry : after) {

			dataFilesTable.setWidget(++rowNo, 0, new Label(dataFileEntry.getTitle()));
			dataFilesTable.setWidget(++rowNo, 1, new Label(dataFileEntry.getSummary()));
			
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
