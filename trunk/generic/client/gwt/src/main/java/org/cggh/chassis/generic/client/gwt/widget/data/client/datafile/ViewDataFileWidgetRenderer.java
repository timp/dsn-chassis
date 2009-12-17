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
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

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
	private DataFileActionsWidget actionsWidget;
	private ViewDataFileWidget owner;
	private FlowPanel contentPanel;

	
	
	
	public ViewDataFileWidgetRenderer(ViewDataFileWidget owner) {
		this.owner = owner;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		this.mainPanel.add(h2Widget("View Data File")); // TODO i18n

		this.renderContentPanel();

		this.renderActionsWidget();
		
		this.setMainPanelStyle();
		
		log.leave();
	}

	
	
	
	
	protected void setMainPanelStyle() {
		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);
	}




	protected void renderActionsWidget() {
		log.enter("renderActionsWidget");
		
		this.actionsWidget = new DataFileActionsWidget();
		this.mainPanel.add(this.actionsWidget); 
		
		log.leave();
	}




	/**
	 * @return
	 */
	protected void renderContentPanel() {
		log.enter("renderContentPanel");
		
		this.contentPanel = new FlowPanel();
		
		this.dataFilePropertiesWidget = new DataFilePropertiesWidget();
		contentPanel.add(this.dataFilePropertiesWidget);
		
		contentPanel.add(h3Widget("Revisions")); // TODO i18n
		contentPanel.add(pWidget("This data file has the following revisions...")); // TODO i18n
		
		this.revisionsWidget = new DataFileRevisionsWidget();
		contentPanel.add(this.revisionsWidget);
		
		this.renderDatasetsSection();

		this.mainPanel.add(contentPanel);

		log.leave();
	}



	
	protected void renderDatasetsSection() {
		log.enter("renderDatasetsSection");

		contentPanel.add(h3Widget("Datasets")); // TODO i18n
		contentPanel.add(pWidget("This data file is included in the following datasets...")); // TODO i18n
		
		this.datasetsWidget = new DataFileDatasetsWidget();
		contentPanel.add(this.datasetsWidget);
		
		log.leave();
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

		if (this.actionsWidget != null) {
			
			HandlerRegistration a = this.actionsWidget.addEditDataFileActionHandler(new DataFileActionHandler() {
				
				public void onAction(DataFileActionEvent e) {
					
					// augment event and bubble 
					e.setEntry(model.getEntry());
					owner.fireEvent(e);
					
				}
			});
			
			HandlerRegistration b = this.actionsWidget.addUploadRevisionActionHandler(new DataFileActionHandler() {
				
				public void onAction(DataFileActionEvent e) {

					// augment event and bubble 
					e.setEntry(model.getEntry());
					owner.fireEvent(e);

				}
			});

			this.childWidgetEventHandlerRegistrations.add(a);
			this.childWidgetEventHandlerRegistrations.add(b);

		}
		
		if (this.datasetsWidget != null) {
			
			HandlerRegistration c = this.datasetsWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
				
				public void onAction(DatasetActionEvent e) {
					
					// just bubble
					owner.fireEvent(e);
					
				}
				
			});
			
			// store handler registrations for later
			this.childWidgetEventHandlerRegistrations.add(c);

		}

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
			if (this.datasetsWidget != null) this.datasetsWidget.setEntry(entry);
		}
		else {
			this.dataFilePropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
			this.revisionsWidget.setEntry(null); // TODO review this, rather call reset() ?
			if (this.datasetsWidget != null) this.datasetsWidget.setEntry(null);
		}
		
		log.leave();
	}
	
	
	
	

}
