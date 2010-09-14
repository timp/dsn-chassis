/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;



import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
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
 * @author raok
 *
 */
public class ViewStudyWidgetRenderer 
	extends AsyncWidgetRenderer<AtomCrudWidgetModel<StudyEntry>>


{

	
	
	
	private Log log = LogFactory.getLog(ViewStudyWidgetRenderer.class);
	
	
	
	
	// UI fields
	private StudyPropertiesWidget studyPropertiesWidget;
	private StudyActionsWidget actionsWidget;
	private ViewStudyWidget owner;
	private StudyDatasetsWidget datasetsWidget;




	protected FlowPanel contentPanel;

	
	
	
	/**
	 * Construct a renderer, passing in the panel to use as the renderer's
	 * canvas.
	 * 
	 * @param canvas
	 * @param controller
	 */
	public ViewStudyWidgetRenderer(ViewStudyWidget owner) {
		this.owner = owner;
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		this.mainPanel.add(h2Widget("View Study")); // TODO i18n

		this.renderContentPanel();

		this.renderActionsWidget();
		
		this.setMainPanelStyle();

		log.leave();
	}

	
	
	
	
	protected void setMainPanelStyle() {
		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);
	}




	protected void renderActionsWidget() {
		this.actionsWidget = new StudyActionsWidget();
		this.mainPanel.add(this.actionsWidget);
		this.actionsWidget.getViewAction().setVisible(false); // has to go here, after added to panel
	}




	/**
	 * @return
	 */
	protected void renderContentPanel() {
		log.enter("renderContentPanel");
		
		this.contentPanel = new FlowPanel();
		
		this.studyPropertiesWidget = new StudyPropertiesWidget();
		contentPanel.add(this.studyPropertiesWidget);
		
		this.renderDatasetsSection();

		this.mainPanel.add(contentPanel);

		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	protected void renderDatasetsSection() {
		log.enter("renderDatasetsSection");

		contentPanel.add(h3Widget("Datasets")); // TODO i18n
		contentPanel.add(pWidget("The following datasets are associated with this study...")); // TODO I18N
		
		this.datasetsWidget = new StudyDatasetsWidget();
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

		HandlerRegistration b = this.model.addEntryChangeHandler(new AtomEntryChangeHandler<StudyEntry>() {
			
			public void onEntryChanged(AtomEntryChangeEvent<StudyEntry> e) {
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
			
			HandlerRegistration a = this.actionsWidget.addEditStudyActionHandler(new BubbleStudyActionHandler());
			HandlerRegistration b = this.actionsWidget.addEditStudyQuestionnaireActionHandler(new BubbleStudyActionHandler());
			HandlerRegistration c = this.actionsWidget.addViewStudyQuestionnaireActionHandler(new BubbleStudyActionHandler());

			this.childWidgetEventHandlerRegistrations.add(a);
			this.childWidgetEventHandlerRegistrations.add(b);
			this.childWidgetEventHandlerRegistrations.add(c);

		}
		
		if (this.datasetsWidget != null) {
			
			HandlerRegistration d = this.datasetsWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
				
				public void onAction(DatasetActionEvent e) {
					// just bubble
					owner.fireEvent(e);
				}
			});
			
			this.childWidgetEventHandlerRegistrations.add(d);

		}

		log.leave();

	}
	
	
	
	private class BubbleStudyActionHandler implements StudyActionHandler {

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler#onAction(org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionEvent)
		 */
		public void onAction(StudyActionEvent e) {

			// augment event and bubble
			e.setEntry(model.getEntry());
			owner.fireEvent(e);
			
		}
		
	}

	
	
	

	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		super.syncUI();
		
		if (this.model != null) {

			log.debug("sync properties");
			StudyEntry entry = this.model.getEntry();
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
	protected void syncEntryUI(StudyEntry entry) {
		log.enter("syncEntryUI");
		
		if (entry != null) {
			this.studyPropertiesWidget.setEntry(entry);
			if (this.datasetsWidget != null) this.datasetsWidget.setEntry(entry);
		}
		else {
			this.studyPropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
		}
		
		log.leave();
	}
	
	
	
	

	
}
