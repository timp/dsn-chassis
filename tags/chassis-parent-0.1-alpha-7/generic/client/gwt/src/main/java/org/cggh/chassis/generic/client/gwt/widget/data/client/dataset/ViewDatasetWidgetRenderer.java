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
	protected FlowPanel contentPanel;

	
	
	
	public ViewDatasetWidgetRenderer(ViewDatasetWidget owner) {
		this.owner = owner;
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		this.renderMainHeading();

		this.renderContentPanel();
		
		this.renderActionsWidget();
		
		this.setMainPanelStyle();
		
		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	protected void setMainPanelStyle() {
		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);
	}





	/**
	 * 
	 */
	protected void renderActionsWidget() {
		this.actionsWidget = new DatasetActionsWidget();
		this.mainPanel.add(this.actionsWidget);
	}





	protected void renderMainHeading() {
		this.mainPanel.add(h2Widget("View Dataset")); // TODO i18n
	}
	
	
	
	/**
	 * @return
	 */
	private void renderContentPanel() {
		log.enter("renderContentPanel");
		
		this.contentPanel = new FlowPanel();
		
		this.datasetPropertiesWidget = new DatasetPropertiesWidget();
		contentPanel.add(this.datasetPropertiesWidget);
		
		this.renderStudiesSubHeading();
		contentPanel.add(pWidget("This dataset is associated with the following studies...")); // TODO I18N
		
		this.studiesWidget = new DatasetStudiesWidget();
		contentPanel.add(this.studiesWidget);

		this.renderDataFilesSubHeading();
		contentPanel.add(pWidget("This dataset includes the following data files...")); // TODO I18N
		
		this.dataFilesWidget = new DatasetDataFilesWidget();
		contentPanel.add(this.dataFilesWidget);
		
		this.renderDataSharingSection();

		this.mainPanel.add(this.contentPanel);

		log.leave();
	}
	
	
	
	
	protected void renderDataSharingSection() {

		contentPanel.add(h3Widget("Data Sharing")); // TODO i18n
		
		this.dataSharingWidget = new DatasetDataSharingWidget();
		contentPanel.add(this.dataSharingWidget);
		
	}





	protected void renderDataFilesSubHeading() {
		this.contentPanel.add(h3Widget("Data Files")); // TODO i18n
	}





	protected void renderStudiesSubHeading() {
		this.contentPanel.add(h3Widget("Studies")); // TODO i18n
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

		if (this.actionsWidget != null) {

			HandlerRegistration a = this.actionsWidget.addEditDatasetActionHandler(new DatasetActionHandler() {
				
				public void onAction(DatasetActionEvent e) {
					
					// augment event and bubble
					e.setEntry(model.getEntry());
					owner.fireEvent(e);
					
				}
			});

			this.childWidgetEventHandlerRegistrations.add(a);

		}
		
		HandlerRegistration b = this.studiesWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				// just bubble
				owner.fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(b);

		HandlerRegistration c = this.dataFilesWidget.addViewDataFileActionHandler(new DataFileActionHandler() {
			
			public void onAction(DataFileActionEvent e) {
				// just bubble
				owner.fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(c);

		if (this.dataSharingWidget != null) {
			
			HandlerRegistration d = this.dataSharingWidget.addShareDatasetActionHandler(new DatasetActionHandler() {
				
				public void onAction(DatasetActionEvent e) {
					// augment event and bubble
					e.setEntry(model.getEntry());
					owner.fireEvent(e);
				}
			});
			
			this.childWidgetEventHandlerRegistrations.add(d);

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
			
			if (this.dataSharingWidget != null) {
				this.dataSharingWidget.setEntry(entry);
			}

		}
		else {
			this.datasetPropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
		}
		
		log.leave();
	}
	
	
	
	
	
	
	
}
