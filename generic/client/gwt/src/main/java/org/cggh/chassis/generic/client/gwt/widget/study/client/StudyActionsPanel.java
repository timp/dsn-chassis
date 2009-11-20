/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class StudyActionsPanel extends ChassisWidget {



	

	// utility fields
	private Log log = LogFactory.getLog(StudyActionsPanel.class);
	
	
	
	
	// UI fields
	private Anchor editAction, editQuestionnaireAction, viewQuestionnaireAction;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.clear();
		
		this.editAction = RenderUtils.renderActionAsAnchor("edit study"); // TODO i18n
		this.viewQuestionnaireAction = RenderUtils.renderActionAsAnchor("view study questionnaire"); // TODO i18n
		this.editQuestionnaireAction = RenderUtils.renderActionAsAnchor("edit study questionnaire"); // TODO i18n

		Widget[] actions = {
			this.editAction, 
			this.viewQuestionnaireAction, 
			this.editQuestionnaireAction
		};
			
		FlowPanel actionsPanel = RenderUtils.renderActionsPanel(actions);

		this.add(actionsPanel);

		log.leave();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		this.registerHandlersForChildWidgetEvents();

		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	private void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		HandlerRegistration a = this.editAction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				fireEvent(new EditStudyActionEvent());
			}
		});
		
		HandlerRegistration b = this.editQuestionnaireAction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				fireEvent(new EditStudyQuestionnaireActionEvent());
			}
		});
		
		HandlerRegistration c = this.viewQuestionnaireAction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				fireEvent(new ViewStudyQuestionnaireActionEvent());
			}
		});
		
		// store registrations so we can remove handlers later if necessary
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);
		
		log.leave();
	}

	
	
	
	public HandlerRegistration addEditStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, EditStudyActionEvent.TYPE);
	}
	
	
	
	
	
	public HandlerRegistration addViewStudyQuestionnaireActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyQuestionnaireActionEvent.TYPE);
	}
	
	
	
	
	
	public HandlerRegistration addEditStudyQuestionnaireActionHandler(StudyActionHandler h) {
		return this.addHandler(h, EditStudyQuestionnaireActionEvent.TYPE);
	}
	
	
	
	
	
	
}
