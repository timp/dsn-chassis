/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
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
public class ViewDataFileWidgetRenderer 
	extends AsyncWidgetRenderer<ViewDataFileWidgetModel> {

	
	
	
	private Log log = LogFactory.getLog(ViewDataFileWidgetRenderer.class);
	private DataFilePropertiesWidget dataFilePropertiesWidget;
	private DataFileRevisionsWidget revisionsWidget;

	
	
	
	
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
//		this.actionsPanel = new DataFileActionsPanel();

		log.debug("render main panel");

		this.mainPanel.add(new HTML("<h2>View Data File</h2>")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.COMMON_MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
//		this.mainPanel.add(this.actionsPanel); TODO
		
		log.leave();

	}

	
	
	
	
	/**
	 * @return
	 */
	private Panel renderContentPanel() {
		log.enter("renderContentPanel");
		
		FlowPanel contentPanel = new FlowPanel();
		
		this.dataFilePropertiesWidget = new DataFilePropertiesWidget();
		contentPanel.add(this.dataFilePropertiesWidget);
		
		contentPanel.add(new HTML("<h3>Revisions</h3>"));
		
		this.revisionsWidget = new DataFileRevisionsWidget();
		contentPanel.add(this.revisionsWidget);
		
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

		HandlerRegistration b = this.model.addDataFileEntryChangeHandler(new DataFileEntryChangeHandler() {
			
			public void onDataFileEntryChanged(DataFileEntryChangeEvent e) {
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

			log.debug("sync submission info");
			DataFileEntry entry = this.model.getEntry();
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
	private void updateInfo(DataFileEntry entry) {
		log.enter("updateInfo");
		
		if (entry != null) {
			this.dataFilePropertiesWidget.setEntry(entry);
			this.revisionsWidget.setEntry(entry);
		}
		else {
			this.dataFilePropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
			this.revisionsWidget.setEntry(null); // TODO review this, rather call reset() ?
		}
		
		log.leave();
		
	}
	
	
	
}
