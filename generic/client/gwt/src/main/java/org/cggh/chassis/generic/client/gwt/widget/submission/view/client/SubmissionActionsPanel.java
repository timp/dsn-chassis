/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

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
public class SubmissionActionsPanel extends ChassisWidget {
	
	
	
	
	
	private Log log;
	private Anchor editThisSubmissionAction, uploadDataFileAction;


	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#getName()
	 */
	@Override
	protected String getName() {
		return "submissionActionsPanel";
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#init()
	 */
	@Override
	public void init() {
		log = LogFactory.getLog(SubmissionActionsPanel.class); // instantiate here because this method is called from superclass constructor
		log.enter("init");

		// we won't use a separate model or renderer here, so nothing to do

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.editThisSubmissionAction = RenderUtils.renderActionAsAnchor("edit submission"); // TODO i18n
		this.uploadDataFileAction = RenderUtils.renderActionAsAnchor("upload data file"); // TODO i18n

		Widget[] actions = {
			this.editThisSubmissionAction,
			this.uploadDataFileAction
		};
			
		FlowPanel actionsPanel = RenderUtils.renderActionsPanel(actions);

		this.contentBox.add(actionsPanel);

		log.leave();

	}

	
	
	
	
	/**
	 * Bind this widget to its UI.
	 * 
	 * @see org.cggh.chassis.generic.widget.client.Widget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		// no model, so don't need to register handlers for model changes

		this.registerHandlersForChildWidgetEvents();
		
		log.leave();

	}

	
	
	
	/**
	 * 
	 */
	private void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		HandlerRegistration a = this.editThisSubmissionAction.addClickHandler(new EditSubmissionActionClickHandler());
		HandlerRegistration b = this.uploadDataFileAction.addClickHandler(new UploadDataFileActionClickHandler());

		// store registrations so we can remove handlers later if necessary
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		
		log.leave();
	}


	
	
	
	class EditSubmissionActionClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			// map click event to edit submission action event and fire
			fireEvent(new EditSubmissionActionEvent());
		}

	}

	
	
	
	
	class UploadDataFileActionClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			// map click event to upload data file action event and fire
			fireEvent(new UploadDataFileActionEvent());
		}

	}

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// no model, so nothing to do here

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");

		this.clearUIEventHandlers();

		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.Widget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");

		this.unbindUI();
		// TODO anything else?

		log.leave();

	}

	
	
	
	
	/**
	 * Register interest in edit submission action events.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addEditSubmissionActionHandler(EditSubmissionActionHandler h) {
		return this.addHandler(h, EditSubmissionActionEvent.TYPE);
	}
	
	

	
	/**
	 * Register interest in upload data file action events.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addUploadDataFileActionHandler(UploadDataFileActionHandler h) {
		return this.addHandler(h, UploadDataFileActionEvent.TYPE);
	}
	
	

	
	
	
}
