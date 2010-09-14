/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client.sq;



import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyPropertiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;



/**
 * @author raok
 *
 */
public class EditStudyQuestionnaireWidgetRenderer 
	extends AsyncWidgetRenderer<StudyQuestionnaireWidgetModel>


{

	
	
	
	private Log log = LogFactory.getLog(EditStudyQuestionnaireWidgetRenderer.class);
	private StudyQuestionnaireWidgetController controller;
	
	
	
	
	// UI fields
	private StudyPropertiesWidget studyPropertiesWidget;
	private EditStudyQuestionnaireWidget owner;
	private FlowPanel questionnaireContainer;
	private XQuestionnaire questionnaire;
	private FlowPanel buttonsPanel1;
	private FlowPanel buttonsPanel2;
	private Button saveButton1;
	private Button cancelButton1;
	private Button saveButton2;
	private Button cancelButton2;





	
	
	
	/**
	 * Construct a renderer, passing in the panel to use as the renderer's
	 * canvas.
	 * 
	 * @param canvas
	 * @param controller
	 */
	public EditStudyQuestionnaireWidgetRenderer(EditStudyQuestionnaireWidget owner) {
		this.owner = owner;
	}


	
	
	public void setController(StudyQuestionnaireWidgetController controller) {
		this.controller = controller;
	}
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		log.debug("render content panel");
		
		Panel contentPanel = this.renderContentPanel();
		
		log.debug("render main panel");

		this.mainPanel.add(h2Widget("Edit Study Questionnaire")); // TODO i18n

		this.mainPanel.add(contentPanel);

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
		
		contentPanel.add(h3Widget("Study Questionnaire")); // TODO i18n

		this.renderButtonsPanels();
		
		contentPanel.add(buttonsPanel1);

		this.questionnaireContainer = new FlowPanel();
		contentPanel.add(this.questionnaireContainer);

		contentPanel.add(buttonsPanel2);

		log.leave();
		return contentPanel;
	}
	
	
	
	/**
	 * 
	 */
	private void renderButtonsPanels() {
		
		saveButton1 = new Button("Save Changes");
		cancelButton1 = new Button("Cancel");
		saveButton2 = new Button("Save Changes");
		cancelButton2 = new Button("Cancel");

		buttonsPanel1 = new FlowPanel();
		buttonsPanel2 = new FlowPanel();

		buttonsPanel1.add(saveButton1);
		buttonsPanel1.add(cancelButton1);

		buttonsPanel2.add(saveButton2);
		buttonsPanel2.add(cancelButton2);

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
		
		// TODO handle read only change
		
		log.leave();
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		ClickHandler saveButtonClickHandler = new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				controller.updateEntry(model.getEntry());
				
			}
			
		};
		
		ClickHandler cancelButtonClickHandler = new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				owner.fireEvent(new CancelEvent());

			}
			
		};
			
		saveButton1.addClickHandler(saveButtonClickHandler);
		
		cancelButton1.addClickHandler(cancelButtonClickHandler);
		
		saveButton2.addClickHandler(saveButtonClickHandler);
		
		cancelButton2.addClickHandler(cancelButtonClickHandler);

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
