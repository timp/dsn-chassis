/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

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
public class DatasetActionsWidget extends ChassisWidget {

	
	
	
	

	// utility fields
	private Log log = LogFactory.getLog(DatasetActionsWidget.class);
	
	
	
	
	// UI fields
	private Anchor editAction;

	
	
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.clear();
		
		this.editAction = RenderUtils.renderActionAnchor("edit dataset"); // TODO i18n

		Widget[] actions = {
			this.editAction
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

		// store registrations so we can remove handlers later if necessary
		this.childWidgetEventHandlerRegistrations.add(a);
		
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
			fireEvent(new EditDatasetActionEvent());

		}

	}


	
	
	
	
	public HandlerRegistration addEditDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, EditDatasetActionEvent.TYPE);
	}






}
