/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class ViewAllStudiesWidgetDefaultRenderer implements ViewAllStudiesWidgetModelListener {

	//Expose view elements for testing purposes.
	Panel studiesListPanel = new SimplePanel();
	Panel loadingPanel = new SimplePanel();
	
	private Panel canvas;

	public ViewAllStudiesWidgetDefaultRenderer(Panel canvas) {
		this.canvas = canvas;
		
		initCanvas();
	}

	private void initCanvas() {
		
		//prepare loading panel
		loadingPanel.add(new Label("Loading..."));
				
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetModelListener#onStatusChanged(java.lang.Integer, java.lang.Integer)
	 */
	public void onStatusChanged(Integer before, Integer after) {

		if (after == ViewAllStudiesWidgetModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == ViewAllStudiesWidgetModel.STATUS_LOADED) {
			canvas.clear();
			canvas.add(studiesListPanel);			
		}
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetModelListener#onStudyEntriesChanged(java.util.List, java.util.List)
	 */
	public void onStudyEntriesChanged(List<StudyEntry> before, List<StudyEntry> after) {
		
		//remove old entries
		studiesListPanel.clear();
		
		//Panel to list studies in
		FlexTable studiesTable = new FlexTable();
		int rowNo = 0;		
		
		//add header row
		studiesTable.setWidget(rowNo, 0, new Label("Title"));
		studiesTable.setWidget(rowNo, 1, new Label("Summary"));
		
		for (StudyEntry studyEntry : after) {
			studiesTable.setWidget(++rowNo, 0, new Label(studyEntry.getTitle()));
			studiesTable.setWidget(rowNo, 1, new Label(studyEntry.getSummary()));
		}
		
		studiesListPanel.add(studiesTable);
		
	}

}
