/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.List;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class ViewSubmissionsWidgetDefaultRenderer implements ViewSubmissionsWidgetModelListener {

	
	
	
	
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
		
		//prepare loading panel
		loadingPanel.add(new Label("Loading..."));
				
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetModelListener#onStatusChanged(java.lang.Integer, java.lang.Integer)
	 */
	public void onStatusChanged(Integer before, Integer after) {

		if (after == ViewSubmissionsWidgetModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == ViewSubmissionsWidgetModel.STATUS_LOADED) {
			canvas.clear();
			canvas.add(submissionsListPanel);			
		}
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.viewall.client.ViewAllSubmissionsWidgetModelListener#onSubmissionEntriesChanged(java.util.List, java.util.List)
	 */
	public void onSubmissionEntriesChanged(List<SubmissionEntry> before, List<SubmissionEntry> after) {
		
		//remove old entries
		submissionsListPanel.clear();
		
		//Panel to list submissions in
		FlexTable submissionsTable = new FlexTable();
		int rowNo = 0;		
		
		//add header row
		submissionsTable.setWidget(rowNo, 0, new Label("Title"));
		submissionsTable.setWidget(rowNo, 1, new Label("Summary"));
		
		for (SubmissionEntry submissionEntry : after) {
			submissionsTable.setWidget(++rowNo, 0, new Label(submissionEntry.getTitle()));
			submissionsTable.setWidget(rowNo, 1, new Label(submissionEntry.getSummary()));
			
			//add view submission link
			Label editSubmission = new Label("view");
			editSubmission.addClickHandler(new ViewSubmissionClickHandler(submissionEntry));
			submissionsTable.setWidget(rowNo, 2, editSubmission);
						
		}
		
		submissionsListPanel.add(submissionsTable);
		
	}
	
	//package private to allow testing
	class ViewSubmissionClickHandler implements ClickHandler {
		
		private final SubmissionEntry submissionEntry;
		
		public ViewSubmissionClickHandler(SubmissionEntry submissionEntry) {
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
