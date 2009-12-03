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
public class StudyActionsWidget extends ChassisWidget {



	

	// utility fields
	private Log log = LogFactory.getLog(StudyActionsWidget.class);
	
	
	
	
	// UI fields
	private Anchor viewAction, editAction, editQuestionnaireAction, viewQuestionnaireAction;

	
	
	
	/**
	 * @return the viewAction
	 */
	public Anchor getViewAction() {
		return viewAction;
	}





	/**
	 * @return the editAction
	 */
	public Anchor getEditAction() {
		return editAction;
	}





	/**
	 * @return the editQuestionnaireAction
	 */
	public Anchor getEditQuestionnaireAction() {
		return editQuestionnaireAction;
	}





	/**
	 * @return the viewQuestionnaireAction
	 */
	public Anchor getViewQuestionnaireAction() {
		return viewQuestionnaireAction;
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.clear();
		
		this.viewAction = RenderUtils.renderActionAnchor("view study"); // TODO i18n
		this.editAction = RenderUtils.renderActionAnchor("edit study"); // TODO i18n
		this.viewQuestionnaireAction = RenderUtils.renderActionAnchor("view study questionnaire"); // TODO i18n
		this.editQuestionnaireAction = RenderUtils.renderActionAnchor("edit study questionnaire"); // TODO i18n

		Widget[] actions = {
			this.viewAction,
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
		
		HandlerRegistration a = this.viewAction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				fireEvent(new ViewStudyActionEvent());
			}
		});
		
		HandlerRegistration b = this.editAction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				fireEvent(new EditStudyActionEvent());
			}
		});
		
		HandlerRegistration c = this.editQuestionnaireAction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				fireEvent(new EditStudyQuestionnaireActionEvent());
			}
		});
		
		HandlerRegistration d = this.viewQuestionnaireAction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				fireEvent(new ViewStudyQuestionnaireActionEvent());
			}
		});
		
		// store registrations so we can remove handlers later if necessary
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);
		this.childWidgetEventHandlerRegistrations.add(d);
		
		log.leave();
	}

	
	
	
	public HandlerRegistration addViewStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyActionEvent.TYPE);
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
