/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetActionEvent;
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
	extends AsyncWidgetRenderer<AtomCrudWidgetModel<DataFileEntry>> 

{

	
	
	
	private Log log = LogFactory.getLog(ViewDataFileWidgetRenderer.class);
	private DataFilePropertiesWidget dataFilePropertiesWidget;
	private DataFileRevisionsWidget revisionsWidget;
	private DataFileDatasetsWidget datasetsWidget;
	private DataFileActionsPanel actionsPanel;
	private ViewDataFileWidget owner;

	
	
	
	public ViewDataFileWidgetRenderer(ViewDataFileWidget owner) {
		this.owner = owner;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		log.debug("render content panel");
		
		Panel contentPanel = this.renderContentPanel();

		log.debug("render actions panel");

		this.actionsPanel = new DataFileActionsPanel();

		log.debug("render main panel");

		this.mainPanel.add(new HTML("<h2>View Data File</h2>")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
		this.mainPanel.add(this.actionsPanel); 
		
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
		
		contentPanel.add(new HTML("<h3>Revisions</h3>")); // TODO i18n
		contentPanel.add(new HTML("<p>This data file has the following revisions...</p>")); // TODO i18n
		
		this.revisionsWidget = new DataFileRevisionsWidget();
		contentPanel.add(this.revisionsWidget);
		
		contentPanel.add(new HTML("<h3>Datasets</h3>")); // TODO i18n
		contentPanel.add(new HTML("<p>This data file is included in the following datasets...</p>")); // TODO i18n
		
		this.datasetsWidget = new DataFileDatasetsWidget();
		contentPanel.add(this.datasetsWidget);

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

		HandlerRegistration b = this.model.addEntryChangeHandler(new AtomEntryChangeHandler<DataFileEntry>() {
			
			public void onEntryChanged(AtomEntryChangeEvent<DataFileEntry> e) {
				log.enter("onDataFileEntryChanged");
				
				syncEntryUI(e.getAfter());
				
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

		HandlerRegistration a = this.actionsPanel.addEditDataFileActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {
				
				// augment event and bubble 
				e.setEntry(model.getEntry());
				owner.fireEvent(e);
				
			}
		});
		
		HandlerRegistration b = this.actionsPanel.addUploadRevisionActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {

				// augment event and bubble 
				e.setEntry(model.getEntry());
				owner.fireEvent(e);

			}
		});
		
		HandlerRegistration c = this.datasetsWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				
				// just bubble
				owner.fireEvent(e);
				
			}
			
		});
		
		// store handler registrations for later
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);

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
			this.syncEntryUI(entry);

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
	private void syncEntryUI(DataFileEntry entry) {
		log.enter("updateInfo");
		
		if (entry != null) {
			this.dataFilePropertiesWidget.setEntry(entry);
			this.revisionsWidget.setEntry(entry);
			this.datasetsWidget.setEntry(entry);
		}
		else {
			this.dataFilePropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
			this.revisionsWidget.setEntry(null); // TODO review this, rather call reset() ?
			this.datasetsWidget.setEntry(null);
		}
		
		log.leave();
	}
	
	
	
	

}
