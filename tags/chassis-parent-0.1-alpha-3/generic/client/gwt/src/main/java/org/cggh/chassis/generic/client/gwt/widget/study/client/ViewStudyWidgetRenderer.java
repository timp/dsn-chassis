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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;



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
	private StudyActionsPanel actionsPanel;
	private ViewStudyWidget owner;
	private StudyDatasetsWidget datasetsWidget;

	
	
	
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

		log.debug("render content panel");
		
		Panel contentPanel = this.renderContentPanel();

		log.debug("render actions panel");

		this.actionsPanel = new StudyActionsPanel();
		
		log.debug("render main panel");

		this.mainPanel.add(h2("View Study")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
		this.mainPanel.add(this.actionsPanel);

		this.actionsPanel.getViewAction().setVisible(false); // has to go here, after added to panel

		log.leave();
	}

	
	
	
	
	/**
	 * @return
	 */
	private Panel renderContentPanel() {
		log.enter("renderContentPanel");
		
		FlowPanel contentPanel = new FlowPanel();
		
		this.studyPropertiesWidget = new StudyPropertiesWidget();
		contentPanel.add(this.studyPropertiesWidget);
		
		contentPanel.add(h3("Datasets")); // TODO i18n
		contentPanel.add(p("The following datasets are associated with this study...")); // TODO I18N
		
		this.datasetsWidget = new StudyDatasetsWidget();
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

		HandlerRegistration a = this.actionsPanel.addEditStudyActionHandler(new BubbleStudyActionHandler());
		HandlerRegistration b = this.actionsPanel.addEditStudyQuestionnaireActionHandler(new BubbleStudyActionHandler());
		HandlerRegistration c = this.actionsPanel.addViewStudyQuestionnaireActionHandler(new BubbleStudyActionHandler());
		
		HandlerRegistration d = this.datasetsWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				// just bubble
				owner.fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);
		this.childWidgetEventHandlerRegistrations.add(d);

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
	private void syncEntryUI(StudyEntry entry) {
		log.enter("updateInfo");
		
		if (entry != null) {
			this.studyPropertiesWidget.setEntry(entry);
			this.datasetsWidget.setEntry(entry);
		}
		else {
			this.studyPropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
		}
		
		log.leave();
	}
	
	
	
	

	
}
