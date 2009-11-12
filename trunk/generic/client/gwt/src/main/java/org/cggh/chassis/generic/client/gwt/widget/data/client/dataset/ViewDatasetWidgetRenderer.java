/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
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
public class ViewDatasetWidgetRenderer 
	extends AsyncWidgetRenderer<ViewDatasetWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(ViewDatasetWidgetRenderer.class);
	private DatasetPropertiesWidget datasetPropertiesWidget;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		log.debug("render content panel");
		
		Panel contentPanel = this.renderContentPanel();

		log.debug("render actions panel");

		// TODO
		
		log.debug("render main panel");

		this.mainPanel.add(new HTML("<h2>View Dataset</h2>")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);

		log.leave();
	}

	
	
	
	/**
	 * @return
	 */
	private Panel renderContentPanel() {
		log.enter("renderContentPanel");
		
		FlowPanel contentPanel = new FlowPanel();
		
		this.datasetPropertiesWidget = new DatasetPropertiesWidget();
		contentPanel.add(this.datasetPropertiesWidget);
		
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

		HandlerRegistration b = this.model.addDatasetEntryChangeHandler(new DatasetEntryChangeHandler() {
			
			public void onChange(DatasetEntryChangeEvent e) {
				log.enter("onDataFileEntryChanged");
				
				updateInfo(e.getAfter());
				
				log.leave();
			}
		});

		log.debug("store registrations so can remove later if necessary");
		this.modelChangeHandlerRegistrations.add(b);
		
		log.leave();
	}




	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		// TODO Auto-generated method stub

		log.leave();

	}

	
	
	

	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		super.syncUI();
		
		if (this.model != null) {

			log.debug("sync properties");
			DatasetEntry entry = this.model.getEntry();
			this.updateInfo(entry);

		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?
			log.warn("model is null, not updating anything");

		}
		
		log.leave();
	}





	/**
	 * @param entry
	 */
	private void updateInfo(DatasetEntry entry) {
		log.enter("updateInfo");
		
		if (entry != null) {
			this.datasetPropertiesWidget.setEntry(entry);
		}
		else {
			this.datasetPropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
		}
		
		log.leave();
	}
	
	
	
	
}
