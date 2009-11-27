/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
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

		this.mainPanel.add(h2("Share Dataset"));
		
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
		
		panel.add(h3("Data-Sharing Agreement"));

		panel.add(p("Please review the data-sharing agreement below. If you are happy with the terms of the agreement, please click the \"accept\" button.")); // TODO i18n and review text

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
		// TODO Auto-generated method stub
		
	}





	/**
	 * 
	 */
	private void renderDatasetAlreadySharedPanel() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		this.model.addDatasetEntryChangeHandler(new DatasetEntryChangeHandler() {
			
			public void onChange(DatasetEntryChangeEvent e) {
				syncUIWithDatasetEntry(e.getAfter());
			}
			
		});
	}




	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		this.acceptButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				// TODO invoke controller to create a submission
				
			}
			
		});
		
		this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				owner.fireEvent(new CancelEvent());
			}
			
		});
		
	}
	
	
	
	

	/**
	 * @param entry
	 */
	protected void syncUIWithDatasetEntry(DatasetEntry entry) {

		this.datasetPropertiesWidget.setEntry(entry);
		
	}

	
	
	
}
