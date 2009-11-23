/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client.sq;



import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionsPanel;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyPropertiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;



/**
 * @author raok
 *
 */
public class ViewStudyQuestionnaireWidgetRenderer 
	extends AsyncWidgetRenderer<StudyQuestionnaireWidgetModel>


{

	
	
	
	private Log log = LogFactory.getLog(ViewStudyQuestionnaireWidgetRenderer.class);
	
	
	
	
	// UI fields
	private StudyPropertiesWidget studyPropertiesWidget;
	private StudyActionsPanel actionsPanel;
	private ViewStudyQuestionnaireWidget owner;
	private FlowPanel questionnaireContainer;
	private XQuestionnaire questionnaire;

	
	
	
	/**
	 * Construct a renderer, passing in the panel to use as the renderer's
	 * canvas.
	 * 
	 * @param canvas
	 * @param controller
	 */
	public ViewStudyQuestionnaireWidgetRenderer(ViewStudyQuestionnaireWidget owner) {
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

		this.mainPanel.add(new HTML("<h2>View Study Questionnaire</h2>")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.MAINWITHACTIONS);
		this.mainPanel.add(contentPanel);
		this.mainPanel.add(this.actionsPanel);
		
		this.actionsPanel.getViewQuestionnaireAction().setVisible(false); // must go here, after actions panel has been added

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
		
		contentPanel.add(new HTML("<h3>Study Questionnaire</h3>")); // TODO i18n
		
		this.questionnaireContainer = new FlowPanel();
		contentPanel.add(this.questionnaireContainer);

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
		HandlerRegistration b = this.actionsPanel.addViewStudyActionHandler(new BubbleStudyActionHandler());
		HandlerRegistration c = this.actionsPanel.addEditStudyQuestionnaireActionHandler(new BubbleStudyActionHandler());
		HandlerRegistration d = this.actionsPanel.addViewStudyQuestionnaireActionHandler(new BubbleStudyActionHandler());
		
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
			if (this.questionnaire != null) this.questionnaire.init(entry.getStudy().getElement(), this.model.getReadOnly());
		}
		else {
			this.studyPropertiesWidget.setEntry(null); // TODO review this, rather call reset() ?
			if (this.questionnaire != null) this.questionnaire.init();
		}
		
		log.leave();
	}





	public void setQuestionnaire(XQuestionnaire questionnaire) {
		if (this.questionnaire != null) {
			this.questionnaireContainer.clear();
		}
		this.questionnaire = questionnaire;
		this.questionnaireContainer.add(this.questionnaire);
	}




	public XQuestionnaire getQuestionnaire() {
		return questionnaire;
	}
	
	
	
	

	
}
