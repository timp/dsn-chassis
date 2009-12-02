/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class ViewDatasetWidgetRenderer 
	extends AsyncWidgetRenderer<AtomCrudWidgetModel<DatasetEntry>> {
	
	
	
	
	private Log log = LogFactory.getLog(ViewDatasetWidgetRenderer.class);
	private ViewDatasetWidget owner;
	
	
	
	
	// UI fields, i.e., child widgets
	private DatasetPropertiesWidget datasetPropertiesWidget;
	private DatasetActionsWidget actionsWidget;
	private DatasetStudiesWidget studiesWidget;
	private DatasetDataFilesWidget dataFilesWidget;
	private DatasetDataSharingWidget dataSharingWidget;

	
	
	
	public ViewDatasetWidgetRenderer(ViewDatasetWidget owner) {
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

		this.actionsWidget = new DatasetActionsWidget();
		
		log.debug("render main panel");

		this.mainPanel.add(h2("View Dataset")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
		this.mainPanel.add(this.actionsWidget);

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
		
		contentPanel.add(h3("Studies")); // TODO i18n
		contentPanel.add(p("This dataset is associated with the following studies...")); // TODO I18N
		
		this.studiesWidget = new DatasetStudiesWidget();
		contentPanel.add(this.studiesWidget);

		contentPanel.add(h3("Data Files")); // TODO i18n
		contentPanel.add(p("This dataset includes the following data files...")); // TODO I18N
		
		this.dataFilesWidget = new DatasetDataFilesWidget();
		contentPanel.add(this.dataFilesWidget);

		contentPanel.add(h3("Data Sharing")); // TODO i18n
		
		this.dataSharingWidget = new DatasetDataSharingWidget();
		contentPanel.add(this.dataSharingWidget);
		
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

		HandlerRegistration b = this.model.addEntryChangeHandler(new AtomEntryChangeHandler<DatasetEntry>() {
			
			public void onEntryChanged(AtomEntryChangeEvent<DatasetEntry> e) {
				log.enter("onEntryChanged");
				
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

		HandlerRegistration a = this.actionsWidget.addEditDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				
				// augment event and bubble
				e.setEntry(model.getEntry());
				owner.fireEvent(e);
				
			}
		});
		
		HandlerRegistration b = this.studiesWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				// just bubble
				owner.fireEvent(e);
			}
		});
		
		HandlerRegistration c = this.dataFilesWidget.addViewDataFileActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {
				// just bubble
				owner.fireEvent(e);
			}
		});
		
		HandlerRegistration d = this.dataSharingWidget.addShareDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				// augment event and bubble
				e.setEntry(model.getEntry());
				owner.fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);
		this.childWidgetEventHandlerRegistrations.add(d);

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
	private void syncEntryUI(DatasetEntry entry) {
		log.enter("updateInfo");
		
		if (entry != null) {
			this.datasetPropertiesWidget.setEntry(entry);
			this.studiesWidget.setEntry(entry);
			this.dataFilesWidget.setEntry(entry);
			this.dataSharingWidget.setEntry(entry);
		}
		else {
			this.datasetPropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
		}
		
		log.leave();
	}
	
	
	
	
	
	
	
}
