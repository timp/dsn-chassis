/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class ViewStudiesWidgetDefaultRenderer implements ViewStudiesWidgetModelListener {

	//Expose view elements for testing purposes.
	Panel studiesListPanel = new SimplePanel();
	Panel loadingPanel = new SimplePanel();
	
	private Panel canvas;
	private ViewStudiesWidgetController controller;

	public ViewStudiesWidgetDefaultRenderer(Panel canvas, ViewStudiesWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
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

		if (after == ViewStudiesWidgetModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == ViewStudiesWidgetModel.STATUS_LOADED) {
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
			
			//add view study link
			Label editStudy = new Label("view");
			editStudy.addClickHandler(new ViewStudyClickHandler(studyEntry));
			studiesTable.setWidget(rowNo, 2, editStudy);
						
		}
		
		studiesListPanel.add(studiesTable);
		
	}
	
	//package private to allow testing
	class ViewStudyClickHandler implements ClickHandler {
		
		private final StudyEntry studyEntry;
		
		public ViewStudyClickHandler(StudyEntry studyEntry) {
			this.studyEntry = studyEntry;
		}
		
		public void onClick(ClickEvent arg0) {
			controller.onUserSelectStudy(studyEntry);
		}
	}

	public void setController(ViewStudiesWidgetController controller) {
		this.controller = controller;
	}
	

}
