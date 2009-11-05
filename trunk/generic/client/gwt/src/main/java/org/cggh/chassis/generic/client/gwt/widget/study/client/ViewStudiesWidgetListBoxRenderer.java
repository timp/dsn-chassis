/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class ViewStudiesWidgetListBoxRenderer implements ViewStudiesWidgetModelListener {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	
	//Expose view elements for testing purposes.
	ListBox studiesListBox = new ListBox();
	Panel loadingPanel = new SimplePanel();
	
	private Panel canvas;
	private ViewStudiesWidgetController controller;

	
	
	
	public ViewStudiesWidgetListBoxRenderer(Panel canvas, ViewStudiesWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		initCanvas();
	}

	
	
	
	/**
	 * @param object
	 */
	public ViewStudiesWidgetListBoxRenderer(ViewStudiesWidgetController controller) {
		this.canvas = new FlowPanel();
		this.controller = controller;
		
		initCanvas();
	}

	
	
	
	/**
	 * 
	 */
	public ViewStudiesWidgetListBoxRenderer() {
		this.canvas = new FlowPanel();
		initCanvas();
	}
	
	
	
	
	private void initCanvas() {
		
		//prepare loading panel
		loadingPanel.add(new Label("Loading..."));
				
	}
	
	
	
	
	public void onStatusChanged(Integer before, Integer after) {

		if (after == ViewStudiesWidgetModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == ViewStudiesWidgetModel.STATUS_LOADED) {
			canvas.clear();
			canvas.add(studiesListBox);			
		}
		
	}

	public void onStudyEntriesChanged(List<StudyEntry> before, List<StudyEntry> after) {
		log.enter("onStudyEntriesChanged");

		//remove old entries
		studiesListBox.clear();

		//store a studyEntryId to Study Entry map
		Map<String, StudyEntry> studyEntryIdMap = new HashMap<String, StudyEntry>();
		
		studiesListBox.addItem("(please select a study)", null);
		
		for (StudyEntry study : after) {
			
			String studyEntryId = study.getId();
			String studyDesc = study.getTitle();
			
			log.debug("adding study to list box: "+studyDesc);
			
			studiesListBox.addItem(studyDesc, studyEntryId);
			
			studyEntryIdMap.put(studyEntryId, study);
			
		}
		
		//add changeHandler
		studiesListBox.addChangeHandler(new ViewStudyChangeHandler(studyEntryIdMap));
		
		log.leave();
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

	public void setController(ViewStudiesWidgetController controller) {
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetModelListener#getCanvas()
	 */
	public Panel getCanvas() {
		return this.canvas;
	}
	

}
