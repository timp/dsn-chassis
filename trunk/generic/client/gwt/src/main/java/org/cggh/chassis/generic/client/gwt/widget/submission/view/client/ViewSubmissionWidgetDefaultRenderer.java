/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client.ViewSubmissionDataFilesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 * 
 */
public class ViewSubmissionWidgetDefaultRenderer implements ViewSubmissionWidgetModel.Listener {
	private Log log = LogFactory.getLog(ViewSubmissionWidgetDefaultRenderer.class);	
	
	

	final private Panel canvas;

	FlowPanel loadingPanel = new FlowPanel();
	FlowPanel mainPanel = new FlowPanel();

	private ViewSubmissionDataFilesWidget submissionDataFilesWidget;
	private SubmissionPropertiesWidget submissionPropsWidget;

	private ViewSubmissionWidget owner;





	
	
	
	
	/**
	 * Construct a renderer, allowing the renderer to create its own canvas.
	 * 
	 * @param controller
	 */
	public ViewSubmissionWidgetDefaultRenderer(ViewSubmissionWidget owner) {

		this.owner = owner;
		this.canvas = new FlowPanel();

		//Create dataFiles linked widget to view linked data files
		submissionDataFilesWidget = new ViewSubmissionDataFilesWidget();

		initCanvas();
	}

	
	
	
	
	private void initCanvas() {
		log.enter("initCanvas");

		this.canvas.addStyleName(CSS.VIEWSUBMISSION_BASE);
		
		this.canvas.add(new HTML("<h2>View Data Submission</h2>"));

		log.debug("prepare loading panel");
		
		this.loadingPanel.add(new Label("loading..."));
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);

		Panel contentPanel = this.renderContentPanel();
		Widget actionsPanel = this.renderActionsPanel();

		log.debug("prepare main panel");

		this.mainPanel.addStyleName(CSS.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
		this.mainPanel.add(actionsPanel);
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);

		log.leave();
	}
	
	
	



	/**
	 * @return
	 */
	private Panel renderContentPanel() {
		log.enter("renderSubmissionDetailsPanel");
		
		submissionPropsWidget = new SubmissionPropertiesWidget();
		
		log.debug("render data files panel");

		FlowPanel dataFilesPanel = new FlowPanel();
		dataFilesPanel.add(new HTML("<h3>Data Files</h3>")); // TODO i18n
		dataFilesPanel.add(this.submissionDataFilesWidget);

		FlowPanel contentPanel = new FlowPanel();
		
		contentPanel.add(submissionPropsWidget);		

		contentPanel.add(dataFilesPanel);		

		log.leave();
		return contentPanel;
	}





	/**
	 * @return
	 */
	private Widget renderActionsPanel() {
		log.enter("renderActionsPanel");
		
		SubmissionActionsPanel actionsPanel = new SubmissionActionsPanel();
		actionsPanel.addEditSubmissionActionHandler(this.owner);
		actionsPanel.addUploadDataFileActionHandler(this.owner);
		
		log.leave();
		return actionsPanel;
	}


	
	
	
	public void renderDataFiles(SubmissionEntry entry) {
		submissionDataFilesWidget.loadDataFilesBySubmissionLink(entry.getEditLink().getHref());
	}

	
	
	
	
	/**
	 * @return
	 */
	public Panel getCanvas() {
		return this.canvas;
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.Listener#onEntryChanged(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry, org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	public void onEntryChanged(SubmissionEntry before, SubmissionEntry entry) {
		log.enter("onEntryChanged");
		
		this.renderSubmissionProps(entry);
		
		this.renderDataFiles(entry);
		
		log.leave();
	}





	/**
	 * @param entry
	 */
	private void renderSubmissionProps(SubmissionEntry entry) {
		log.enter("renderSubmissionProps");
		
		this.submissionPropsWidget.setSubmissionEntry(entry);
		
		log.leave();
		
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.common.client.mvc.ModelBase.Listener#onStatusChanged(int, int)
	 */
	public void onStatusChanged(int before, int after) {

		if (after == ViewSubmissionWidgetModel.STATUS_RETRIEVE_PENDING) {

			this.mainPanel.setVisible(false);
			this.loadingPanel.setVisible(true);

		} else if (after == ViewSubmissionWidgetModel.STATUS_READY) {

			this.loadingPanel.setVisible(false);
			this.mainPanel.setVisible(true);

		} else if (after == ViewSubmissionWidgetModel.STATUS_RETRIEVE_ERROR) {

			// TODO handle error case (could use extra panel or pass error to
			// parent)

		}
		
	}


}
