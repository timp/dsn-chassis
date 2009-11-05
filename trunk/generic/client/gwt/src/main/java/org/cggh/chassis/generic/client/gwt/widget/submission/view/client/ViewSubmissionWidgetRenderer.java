/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.SubmissionEntryChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.ViewSubmissionWidgetModel.SubmissionEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client.ViewSubmissionDataFilesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetRenderer 
	extends AsyncWidgetRenderer<ViewSubmissionWidgetModel> {

	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidgetRenderer.class);

	
	
	private ViewSubmissionWidget owner;



	
	private SubmissionActionsPanel submissionActionsPanel;
	private SubmissionPropertiesWidget submissionPropsWidget;
	private ViewSubmissionDataFilesWidget submissionDataFilesWidget;




	public ViewSubmissionWidgetRenderer(ViewSubmissionWidget owner) {
		this.owner = owner;
	}
	
	
	
	
	/**
	 * 
	 */
	@Override
	public void renderMainPanel() {
		log.enter("renderMainPanel");
		
		log.debug("render content panel");
		
		Panel contentPanel = this.renderContentPanel();

		log.debug("render actions panel");
		
		this.submissionActionsPanel = new SubmissionActionsPanel();

		log.debug("render main panel");

		this.mainPanel.add(new HTML("<h2>View Data Submission</h2>")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
		this.mainPanel.add(this.submissionActionsPanel);
		this.mainPanel.setVisible(false);

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
	 * 
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		super.registerHandlersForModelChanges();

		HandlerRegistration b = this.model.addSubmissionEntryChangeHandler(new SubmissionEntryChangeHandler() {
			
			public void onSubmissionEntryChanged(SubmissionEntryChangeEvent e) {
				log.enter("onSubmissionEntryChanged");
				
				updateSubmissionInfo(e.getAfter());
				
				log.leave();
			}
		});

		log.debug("store registrations so can remove later if necessary");
		this.modelChangeHandlerRegistrations.add(b);
		
		log.leave();
		
	}



	
	/**
	 * 
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
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
		
		super.syncUI();
		
		if (this.model != null) {

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
