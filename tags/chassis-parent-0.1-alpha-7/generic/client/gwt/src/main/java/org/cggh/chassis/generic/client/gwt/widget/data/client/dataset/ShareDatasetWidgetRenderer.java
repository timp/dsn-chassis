/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;

import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

/**
 * @author aliman
 *
 */
public class ShareDatasetWidgetRenderer 
	extends AsyncWidgetRenderer<ShareDatasetWidgetModel> {

	
	
	Log log = LogFactory.getLog(ShareDatasetWidgetRenderer.class);
	private ShareDatasetWidget owner;
	
	
	
	
	// UI fields
	private FlowPanel dataSharingAgreementPanel;
	private FlowPanel datasetSharedSuccessPanel;
	private FlowPanel datasetAlreadySharedPanel;
	private DatasetPropertiesWidget datasetPropertiesWidget;
	private Button acceptButton;
	private Button cancelButton;
	private ShareDatasetWidgetController controller;
	private Set<HasClickHandlers> viewDatasetActions = new HashSet<HasClickHandlers>();





	/**
	 * @param owner
	 */
	public ShareDatasetWidgetRenderer(ShareDatasetWidget owner) {
		this.owner = owner;
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		this.mainPanel.add(h2Widget("Share Dataset"));
		
		this.datasetPropertiesWidget = new DatasetPropertiesWidget();
		
		this.mainPanel.add(datasetPropertiesWidget);

		this.dataSharingAgreementPanel = new FlowPanel();
		this.mainPanel.add(this.dataSharingAgreementPanel);
		
		this.renderDataSharingAgreementPanel();
		
		this.datasetSharedSuccessPanel = new FlowPanel();
		this.mainPanel.add(this.datasetSharedSuccessPanel);
		
		this.renderDatasetSharedSuccessPanel();
		
		this.datasetAlreadySharedPanel = new FlowPanel();
		this.mainPanel.add(this.datasetAlreadySharedPanel);
		
		this.renderDatasetAlreadySharedPanel();
		
		log.leave();
	}





	/**
	 * 
	 */
	private void renderDataSharingAgreementPanel() {
		log.enter("renderDataSharingAgreementPanel");
		
		log.debug("render preamble");
		
		FlowPanel panel = this.dataSharingAgreementPanel;
		
		panel.add(h3Widget("Data-Sharing Agreement"));

		panel.add(pWidget("Please review the data-sharing agreement below. If you are happy with the terms of the agreement, please click the \"accept\" button.")); // TODO i18n and review text

		log.debug("data-sharing agreement url: "+Configuration.getDataSharingAgreementUrl());
		
		Frame dataSharingAgreementFrame = new Frame(Configuration.getDataSharingAgreementUrl());
		
		panel.add(dataSharingAgreementFrame);
		
		FlowPanel buttonsPanel = new FlowPanel();
		
		this.acceptButton = new Button("Accept Data-Sharing Agreement and Share Dataset with "+Configuration.getNetworkName());
		this.cancelButton = new Button("Cancel");
		
		buttonsPanel.add(acceptButton);
		buttonsPanel.add(cancelButton);
		
		panel.add(buttonsPanel);
		
		log.leave();
	}





	/**
	 * 
	 */
	private void renderDatasetSharedSuccessPanel() {

		FlowPanel panel = this.datasetSharedSuccessPanel;
		
		panel.add(h3Widget("Dataset Shared"));
		
		panel.add(pWidget("Your dataset has been successfully shared with "+Configuration.getNetworkName()));
		
		panel.add(pWidget("TODO what happens next..."));
		
		Anchor viewDatasetAction = RenderUtils.renderActionAnchor("back to view dataset...");

		panel.add(viewDatasetAction);

		this.viewDatasetActions.add(viewDatasetAction);
		
	}





	/**
	 * 
	 */
	private void renderDatasetAlreadySharedPanel() {
		
		FlowPanel panel = this.datasetAlreadySharedPanel;
		
		panel.add(h3Widget("Dataset Shared"));
		
		panel.add(pWidget("This dataset has already been shared with "+Configuration.getNetworkName()+"."));


		// TODO Not sure this actually does anything
		Anchor viewDatasetAction = RenderUtils.renderActionAnchor("back to view dataset...");

		panel.add(viewDatasetAction);

		this.viewDatasetActions.add(viewDatasetAction);
		
	}
	
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		this.model.addDatasetEntryChangeHandler(new DatasetEntryChangeHandler() {
			
			public void onChange(DatasetEntryChangeEvent e) {
				syncUIWithDatasetEntry(e.getAfter());
			}
			
		});
		
		this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
			
		});
	}




	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		this.acceptButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				controller.shareDataset();
				
			}
			
		});
		
		this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				owner.fireEvent(new CancelEvent());
			}
			
		});
		
		for (HasClickHandlers action : this.viewDatasetActions) {
		
			action.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					owner.fireViewDatasetActionEvent();
				}
				
			});
		}
		
	}
	
	
	
	
	@Override
	public void syncUI() {
		
		super.syncUI();
		
		this.syncUIWithDatasetEntry(this.model.getDatasetEntry());
		this.syncUIWithStatus(this.model.getStatus());
		
	}
	
	

	/**
	 * @param entry
	 */
	protected void syncUIWithDatasetEntry(DatasetEntry entry) {

		this.datasetPropertiesWidget.setEntry(entry);
		
	}



	
	/**
	 * @param status
	 */
	private void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			
			this.dataSharingAgreementPanel.setVisible(false);
			this.datasetSharedSuccessPanel.setVisible(false);
			this.datasetAlreadySharedPanel.setVisible(false);
			
		}
		else if (status instanceof ShareDatasetWidgetModel.DatasetRetrievedStatus) {

			log.debug("has dataset already been shared?");
			
			boolean alreadyShared = (this.model.getDatasetEntry().getSubmissionLink() != null);
			
			if (alreadyShared) {

				log.debug("already shared");
				
				this.dataSharingAgreementPanel.setVisible(false);
				this.datasetSharedSuccessPanel.setVisible(false);
				this.datasetAlreadySharedPanel.setVisible(true);
				
			}
			else {
			
				log.debug("not yet shared");
				
				this.dataSharingAgreementPanel.setVisible(true);
				this.datasetSharedSuccessPanel.setVisible(false);
				this.datasetAlreadySharedPanel.setVisible(false);
				
			}
			
		}
		else if (status instanceof ShareDatasetWidgetModel.DatasetSharedStatus) {
			
			this.dataSharingAgreementPanel.setVisible(false);
			this.datasetSharedSuccessPanel.setVisible(true);
			this.datasetAlreadySharedPanel.setVisible(false);
			
		}
		
		log.leave();
	}





	/**
	 * @param controller
	 */
	public void setController(ShareDatasetWidgetController controller) {
		this.controller = controller;
	}

	
	
	
}
