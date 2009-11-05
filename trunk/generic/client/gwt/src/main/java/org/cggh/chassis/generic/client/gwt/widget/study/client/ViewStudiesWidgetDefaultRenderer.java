/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
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
public class ViewStudiesWidgetDefaultRenderer implements ViewStudiesWidgetRenderer {

	
	
	
	//Expose view elements for testing purposes.
	Panel studiesListPanel = new SimplePanel();
	Panel loadingPanel = new SimplePanel();
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas;
	private ViewStudiesWidgetController controller;
	private String selectStudyLinkText = "";

	
	
	
	/**
	 * Construct a renderer, passing in the panel to use as the renderer's 
	 * canvas. Use this if a select study link is not required.
	 * 
	 * @param canvas
	 * @param controller
	 */
	public ViewStudiesWidgetDefaultRenderer(Panel canvas, ViewStudiesWidgetController controller) {
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
	public ViewStudiesWidgetDefaultRenderer(Panel canvas, ViewStudiesWidgetController controller, String selectStudyLinkText) {
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
	public ViewStudiesWidgetDefaultRenderer(ViewStudiesWidgetController controller, String selectStudyLinkText) {
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

		if (after == ViewStudiesWidgetModel.STATUS_LOADING) {

//			canvas.clear();
//			canvas.add(loadingPanel);
			
			this.studiesListPanel.setVisible(false);
			this.loadingPanel.setVisible(true);
			
		} else if (after == ViewStudiesWidgetModel.STATUS_LOADED) {

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

		FlexTable studiesTable = new FlexTable();
		studiesTable.setCellPadding(0);
		studiesTable.setCellSpacing(0);
		studiesTable.addStyleName(CommonStyles.VIEWSTUDIES_STUDIESTABLE);
		int rowNo = 0;		
		
		log.debug("add header row");

//		String[] headers = { "Title", "Summary", "Modules", "Owners", "Created", "Updated", "Actions" };
		String[] headers = { "Title", "Summary", "Modules", "Owners", "Actions" };
		
		for (int i=0; i<headers.length; i++) {
			Label headerLabel = new Label(headers[i]);
			headerLabel.addStyleName(CommonStyles.VIEWSTUDIES_TABLEHEADER);
			studiesTable.setWidget(rowNo, i, headerLabel);
		}
		
		log.debug("add studies");
		
		for (StudyEntry studyEntry : after) {

			String title = studyEntry.getTitle();
			
			String summary = studyEntry.getSummary();
			int cutoff = 100;
			if (summary.length() > cutoff) {
				log.debug("truncate long summary");
				summary = summary.substring(0, cutoff) + "...";
			}
			
			List<String> modules = studyEntry.getStudy().getModules();
			Map<String,String> moduleLabels = ConfigurationBean.getModules();
			String modulesContent = "";
			for (Iterator<String> it = modules.iterator(); it.hasNext(); ) {
				String ml = moduleLabels.get(it.next());
				modulesContent += ml;
				if (it.hasNext()) {
					modulesContent += ", ";
				}
			}
			
//			String created = studyEntry.getPublished();
//			String updated = studyEntry.getUpdated();
			
			Label studyTitleLabel = new Label(title);
			studyTitleLabel.addStyleName(CommonStyles.VIEWSTUDIES_STUDYTITLE);
			studiesTable.setWidget(++rowNo, 0, studyTitleLabel);
			
			studiesTable.setWidget(rowNo, 1, new Label(summary));
			studiesTable.setWidget(rowNo, 2, new Label(modulesContent));
			
			String authorsContent = "";
			for (Iterator<AtomAuthor> it = studyEntry.getAuthors().iterator(); it.hasNext(); ) {
				authorsContent += it.next().getEmail();
				if (it.hasNext()) {
					authorsContent += ", ";
				}
			}
			
			studiesTable.setWidget(rowNo, 3, new Label(authorsContent));

//			studiesTable.setWidget(rowNo, 4, new Label(created));
//			studiesTable.setWidget(rowNo, 5, new Label(updated));
			
			log.debug("add a select study link");
			Anchor selectStudy = new Anchor();
			selectStudy.setText(selectStudyLinkText);
			selectStudy.addStyleName(CommonStyles.COMMON_ACTION);
			selectStudy.addClickHandler(new SelectStudyClickHandler(studyEntry));
			studiesTable.setWidget(rowNo, 4, selectStudy);
						
		}
		
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
