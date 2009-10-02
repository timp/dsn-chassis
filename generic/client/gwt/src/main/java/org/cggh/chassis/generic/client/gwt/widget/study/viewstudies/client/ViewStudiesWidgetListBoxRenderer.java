/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class ViewStudiesWidgetListBoxRenderer implements ViewAllStudiesWidgetModelListener {

	//Expose view elements for testing purposes.
	ListBox studiesListBox = new ListBox();
	Panel loadingPanel = new SimplePanel();
	
	private Panel canvas;
	private ViewAllStudiesWidgetController controller;

	public ViewStudiesWidgetListBoxRenderer(Panel canvas, ViewAllStudiesWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		initCanvas();
	}

	private void initCanvas() {
		
		//prepare loading panel
		loadingPanel.add(new Label("Loading..."));
				
	}
	
	public void onStatusChanged(Integer before, Integer after) {

		if (after == ViewAllStudiesWidgetModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == ViewAllStudiesWidgetModel.STATUS_LOADED) {
			canvas.clear();
			canvas.add(studiesListBox);			
		}
		
	}

	public void onStudyEntriesChanged(List<StudyEntry> before, List<StudyEntry> after) {

		//remove old entries
		studiesListBox.clear();

		//store a studyEntryId to Study Entry map
		Map<String, StudyEntry> studyEntryIdMap = new HashMap<String, StudyEntry>();
		
		studiesListBox.addItem("Please select", "-1");
		
		for (StudyEntry study : after) {
			
			String studyEntryId = study.getId();
			String studyDesc = study.getTitle();
			
			studiesListBox.addItem(studyDesc, studyEntryId);
			
			studyEntryIdMap.put(studyEntryId, study);
			
		}
		
		//add changeHandler
		studiesListBox.addChangeHandler(new ViewStudyChangeHandler(studyEntryIdMap));
				
	}
	
	class ViewStudyChangeHandler implements ChangeHandler {

		private Map<String, StudyEntry> studyEntryIdMap;

		public ViewStudyChangeHandler(Map<String, StudyEntry> studyEntryIdMap) {
			this.studyEntryIdMap = studyEntryIdMap;
		}

		public void onChange(ChangeEvent arg0) {
			
			String selectedStudyEntryId = studiesListBox.getValue(studiesListBox.getSelectedIndex());
			
			StudyEntry selectedStudyEntry = studyEntryIdMap.get(selectedStudyEntryId);
			
			controller.onUserSelectStudy(selectedStudyEntry);
			
		}
		
	}

	public void setController(ViewAllStudiesWidgetController controller) {
		this.controller = controller;
	}
	

}
