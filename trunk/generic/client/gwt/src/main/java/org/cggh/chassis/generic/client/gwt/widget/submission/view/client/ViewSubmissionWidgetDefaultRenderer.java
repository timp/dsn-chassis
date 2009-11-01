/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.StatusChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.SubmissionEntryChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.SubmissionEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client.ViewSubmissionDataFilesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetDefaultRenderer extends ChassisWidgetRenderer {

	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidgetDefaultRenderer.class);

	
	
	private ViewSubmissionWidget owner;
	private ViewSubmissionWidgetModel model;



	
	private FlowPanel loadingPanel, mainPanel;
	private SubmissionActionsPanel submissionActionsPanel;
	private SubmissionPropertiesWidget submissionPropsWidget;
	private ViewSubmissionDataFilesWidget submissionDataFilesWidget;




	public ViewSubmissionWidgetDefaultRenderer(ViewSubmissionWidget owner) {
		this.owner = owner;
	}
	
	
	
	
	/**
	 * 
	 */
	public void renderUI() {
		log.enter("renderUI");
		
		this.canvas.clear();
		
		this.canvas.add(new HTML("<h2>View Data Submission</h2>")); // TODO i18n

		log.debug("render loading panel");
		
		this.loadingPanel = new FlowPanel();
		this.loadingPanel.add(new Label("loading...")); // TODO i18n
		this.loadingPanel.setVisible(false);
		this.canvas.add(this.loadingPanel);

		log.debug("render content panel");
		
		Panel contentPanel = this.renderContentPanel();

		log.debug("render actions panel");
		
		this.submissionActionsPanel = new SubmissionActionsPanel();

		log.debug("render main panel");

		this.mainPanel = new FlowPanel();
		this.mainPanel.addStyleName(CSS.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
		this.mainPanel.add(this.submissionActionsPanel);
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);

		log.leave();
		
	}

	
	
	
	/**
	 * @return
	 */
	private Panel renderContentPanel() {
		log.enter("renderSubmissionDetailsPanel");
		
		this.submissionPropsWidget = new SubmissionPropertiesWidget();
		this.submissionDataFilesWidget = new ViewSubmissionDataFilesWidget();
		
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
	 * @param model
	 */
	public void bindUI(ViewSubmissionWidgetModel model) {
		log.enter("bindUI");
		
		log.debug("unbind to clear anything");
		this.unbindUI();
		
		log.debug("keep reference to model");
		this.model = model;
		
		log.debug("register this as handler for model property change events");
		this.registerHandlersForModelChanges();
		
		log.debug("register handlers for child widget events");
		this.registerHandlersForChildWidgetEvents();
		
		log.leave();
	}




	/**
	 * 
	 */
	private void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		HandlerRegistration a = this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				log.enter("onStatusChanged");
				
				log.debug("before: "+e.getBefore()+"; after: "+e.getAfter());
				
				updatePanelVisibility(e.getAfter());
				
				log.leave();
			}
		});
		
		HandlerRegistration b = this.model.addSubmissionEntryChangeHandler(new SubmissionEntryChangeHandler() {
			
			public void onSubmissionEntryChanged(SubmissionEntryChangeEvent e) {
				log.enter("onSubmissionEntryChanged");
				
				updateSubmissionInfo(e.getAfter());
				
				log.leave();
			}
		});

		log.debug("store registrations so can remove later if necessary");
		this.modelChangeHandlerRegistrations.add(a);
		this.modelChangeHandlerRegistrations.add(b);
		
		log.leave();
		
	}



	
	/**
	 * 
	 */
	private void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		log.debug("register handler for edit submission action");
		
		HandlerRegistration a = this.submissionActionsPanel.addEditSubmissionActionHandler(new EditSubmissionActionHandler() {
			
			public void onActionEditSubmission(EditSubmissionActionEvent e) {
				log.enter("[anon EditSubmissionActionHandler] onActionEditSubmission");
				
				log.debug("augment event");
				e.setSubmissionEntry(model.getSubmissionEntry());
				
				log.debug("do nothing else, just bubble event");
				owner.fireEvent(e);
				
				log.leave();
			}
			
		});
		
		log.debug("register handler for upload data file action");
		
		HandlerRegistration b = this.submissionActionsPanel.addUploadDataFileActionHandler(new UploadDataFileActionHandler() {
			
			public void onActionUploadDataFile(UploadDataFileActionEvent e) {
				log.enter("[anon UploadDataFileActionHandler] onActionUploadDataFile");
				
				log.debug("augment event");
				e.setSubmissionEntry(model.getSubmissionEntry());
				
				log.debug("do nothing else, bubble event");
				owner.fireEvent(e);
				
				log.leave();
			}
		});
		
		log.debug("store registrations so can remove later if necessary");
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		
		log.leave();
	}






	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		if (this.model != null) {

			log.debug("sync panel visibility");
			this.updatePanelVisibility(this.model.getStatus());
			
			log.debug("sync submission info");
			SubmissionEntry entry = this.model.getSubmissionEntry();
			this.updateSubmissionInfo(entry);

		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?
			log.warn("model is null, not updating anything");

		}
		
		
		log.leave();
		
	}




	/**
	 * 
	 */
	public void unbindUI() {
		log.enter("unbindUI");
		
		this.clearModelChangeHandlers();
		this.clearChildWidgetEventHandlers();
		
		log.leave();
		
	}


	
	
	private void updatePanelVisibility(int status) {

		if ( status == ViewSubmissionWidgetModel.STATUS_INITIAL) {

			this.mainPanel.setVisible(false);
			this.loadingPanel.setVisible(false);

		} 
		else if ( status == ViewSubmissionWidgetModel.STATUS_RETRIEVE_PENDING ) {

			this.mainPanel.setVisible(false);
			this.loadingPanel.setVisible(true);

		} 
		else if ( status == ViewSubmissionWidgetModel.STATUS_READY ) {

			this.loadingPanel.setVisible(false);
			this.mainPanel.setVisible(true);

		} 
		else if ( status == ViewSubmissionWidgetModel.STATUS_ERROR ) {

			// TODO handle error case (could use extra panel or pass error to
			// parent)
			
			log.error("unexpected error", new Error("unexpected error"));

		}
		else {

			// TODO review this
			log.error("unexpected status change", new Error("unexpected status change"));

		}
		
	}




	/**
	 * @param after
	 */
	private void updateSubmissionInfo(SubmissionEntry entry) {
		log.enter("updateSubmissionInfo");
		
		if (entry != null) {

			this.submissionPropsWidget.setSubmissionEntry(entry);
			this.submissionDataFilesWidget.loadDataFilesBySubmissionLink(Configuration.getSubmissionFeedURL() + entry.getEditLink().getHref());

		}
		else {
			
			this.submissionPropsWidget.setSubmissionEntry(null);
			this.submissionDataFilesWidget.loadDataFilesBySubmissionLink(null); // TODO review this, rather reset() or something like that?
			
		}
		
		log.leave();
		
	}



	
}
