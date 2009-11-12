/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.user.client.ui.HTML;

/**
 * @author aliman
 *
 */
public class ViewDatasetWidgetRenderer 
	extends AsyncWidgetRenderer<ViewDatasetWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(ViewDatasetWidgetRenderer.class);

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		log.debug("render main panel");

		this.mainPanel.add(new HTML("<h2>View Dataset</h2>")); // TODO i18n

		this.mainPanel.addStyleName(CommonStyles.COMMON_MAINWITHACTIONS);

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

	
	
	
	
}
