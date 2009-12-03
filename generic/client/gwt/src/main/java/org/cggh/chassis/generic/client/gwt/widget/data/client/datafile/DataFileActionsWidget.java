/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

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
public class DataFileActionsWidget extends ChassisWidget {

	
	
	
	

	// utility fields
	private Log log;
	
	
	
	
	// UI fields
	private Anchor editAction, uploadRevisionAction;

	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		// no state variables, nothing to do

		log.leave();

	}

	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataFileActionsWidget.class);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.clear();
		
		this.editAction = RenderUtils.renderActionAnchor("edit data file"); // TODO i18n
		this.uploadRevisionAction = RenderUtils.renderActionAnchor("upload a new revision"); // TODO i18n

		Widget[] actions = {
			this.editAction,
			this.uploadRevisionAction
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
		
		HandlerRegistration a = this.editAction.addClickHandler(new EditActionClickHandler());
		HandlerRegistration b = this.uploadRevisionAction.addClickHandler(new UploadRevisionActionClickHandler());

		// store registrations so we can remove handlers later if necessary
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		
		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// no state variables, so nothing to do

		log.leave();

	}

	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	private class EditActionClickHandler implements ClickHandler {

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		public void onClick(ClickEvent arg0) {

			// map click event to action event and fire
			fireEvent(new EditDataFileActionEvent());

		}

	}



	/**
	 * @author aliman
	 *
	 */
	private class UploadRevisionActionClickHandler implements ClickHandler {

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		public void onClick(ClickEvent arg0) {

			// map click event to action event and fire
			fireEvent(new UploadDataFileRevisionActionEvent());

		}

	}
	
	
	
	
	public HandlerRegistration addEditDataFileActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, EditDataFileActionEvent.TYPE);
	}





	public HandlerRegistration addUploadRevisionActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, UploadDataFileRevisionActionEvent.TYPE);
	}






}
