/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;

import com.google.gwt.user.client.ui.HTML;

/**
 * @author aliman
 *
 */
public class NewDatasetWidgetRenderer 
	extends ChassisWidgetRenderer<NewDatasetWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(NewDatasetWidgetRenderer.class);

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.canvas.add(new HTML("<h2>New Dataset</h2>")); // TODO i18n

		this.canvas.add(new HTML("<p>TODO</p>")); // TODO i18n

		log.leave();

	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		// TODO Auto-generated method stub

		log.leave();

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForModelChanges()
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");

		// TODO Auto-generated method stub

		log.leave();

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// TODO Auto-generated method stub

		log.leave();

	}
	
	
	

}
