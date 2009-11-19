/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
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
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class MyStudiesWidgetDefaultRenderer implements MyStudiesWidgetRenderer {

	
	
	
	//Expose view elements for testing purposes.
	Panel studiesListPanel = new SimplePanel();
	Panel loadingPanel = new SimplePanel();
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas;
	private MyStudiesWidgetController controller;
	private String selectStudyLinkText = "";

	
	
	
	/**
	 * Construct a renderer, passing in the panel to use as the renderer's 
	 * canvas. Use this if a select study link is not required.
	 * 
	 * @param canvas
	 * @param controller
	 */
	public MyStudiesWidgetDefaultRenderer(Panel canvas, MyStudiesWidgetController controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		initCanvas();
	}
	
	
	
	
	/**
	 * Construct a renderer, passing in the panel to use as the renderer's 
	 * canvas.
	 * 
	 * @param canvas
	 * @param controller
	 * @param selectStudyLinkText
	 */
	public MyStudiesWidgetDefaultRenderer(Panel canvas, MyStudiesWidgetController controller, String selectStudyLinkText) {
		this.canvas = canvas;
		this.controller = controller;
		this.selectStudyLinkText = selectStudyLinkText;
		
		initCanvas();
	}

	
	
	
	/**
	 * Construct a renderer, allowing the renderer to create its own canvas.
	 * 
	 * @param controller
	 * @param selectStudyLinkText
	 */
	public MyStudiesWidgetDefaultRenderer(MyStudiesWidgetController controller, String selectStudyLinkText) {
		this.canvas = new FlowPanel();
		this.controller = controller;
		this.selectStudyLinkText = selectStudyLinkText;
		
		initCanvas();
	}

	
	
	
	
	private void initCanvas() {
		log.enter("initCanvas");
		
		this.canvas.addStyleName(CommonStyles.VIEWSTUDIES_BASE);
		
		this.canvas.add(new HTML("<h2>My Studies</h2>"));
		
		this.canvas.add(new HTML("<p>Listed below are all of the studies that you own.</p>"));
		
		log.debug("prepare loading panel");
		this.loadingPanel.add(new Label("Loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);
		
		log.debug("prepare studies list panel");
		this.studiesListPanel.setVisible(false);
		this.canvas.add(this.studiesListPanel);
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetModelListener#onStatusChanged(java.lang.Integer, java.lang.Integer)
	 */
	public void onStatusChanged(Integer before, Integer after) {

		if (after == MyStudiesWidgetModel.STATUS_LOADING) {

//			canvas.clear();
//			canvas.add(loadingPanel);
			
			this.studiesListPanel.setVisible(false);
			this.loadingPanel.setVisible(true);
			
		} else if (after == MyStudiesWidgetModel.STATUS_LOADED) {

//			canvas.clear();
//			canvas.add(studiesListPanel);			

			this.loadingPanel.setVisible(false);
			this.studiesListPanel.setVisible(true);

		}
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetModelListener#onStudyEntriesChanged(java.util.List, java.util.List)
	 */
	public void onStudyEntriesChanged(List<StudyEntry> before, List<StudyEntry> after) {
		log.enter("onStudyEntriesChanged");
		
		log.debug("remove old entries");

		studiesListPanel.clear();
		
		log.debug("create table to list studies in");
		
		List<Widget[]> rows = new ArrayList<Widget[]>();

		Widget[] headerRow = { 
				new Label("Title"),
				new Label("Summary"),
				new Label("Modules"),
				new Label("Owners"),
				new Label("Actions")
		};

		rows.add(headerRow);
		
		log.debug("add studies");
		
		for (StudyEntry studyEntry : after) {

			String title = studyEntry.getTitle();
			String summary = RenderUtils.truncate(studyEntry.getSummary(), 100);
			String modules = RenderUtils.renderModulesAsCommaDelimitedString(studyEntry.getStudy().getModules(), Configuration.getModules());
			String authors = RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(studyEntry.getAuthors());
			
			log.debug("add a select study link");
			Anchor selectStudy = new Anchor();
			selectStudy.setText(selectStudyLinkText);
			selectStudy.addStyleName(CommonStyles.COMMON_ACTION);
			selectStudy.addClickHandler(new SelectStudyClickHandler(studyEntry));
			FlowPanel actionsPanel = new FlowPanel();
			actionsPanel.add(selectStudy);
			
			Widget[] row = {
				new Label(title),
				new Label(summary),
				new Label(modules),
				new Label(authors),
				actionsPanel
			};
			
			rows.add(row);
						
		}
		
		FlexTable studiesTable = RenderUtils.renderResultsTable(rows);
		
		studiesListPanel.add(studiesTable);
		
		log.leave();
	}
	
	
	
	
	//package private to allow testing
	class SelectStudyClickHandler implements ClickHandler {
		
		private final StudyEntry studyEntry;
		
		public SelectStudyClickHandler(StudyEntry studyEntry) {
			this.studyEntry = studyEntry;
		}
		
		public void onClick(ClickEvent arg0) {
			controller.onUserSelectStudy(studyEntry);
		}
	}

	
	
	
	public void setController(MyStudiesWidgetController controller) {
		this.controller = controller;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetModelListener#getCanvas()
	 */
	public Panel getCanvas() {
		return this.canvas;
	}
	
	
	

}
