/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;


/**
 * @author aliman
 *
 */
public class SelectCuratorWidgetRenderer 
	extends AsyncWidgetRenderer<SelectCuratorWidgetModel> {

	private SelectCuratorWidgetController controller;

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		// TODO Auto-generated method stub

		this.mainPanel.add(pWidget("Please select a curator from the list below..."));
	}

	/**
	 * @param controller
	 */
	public void setController(SelectCuratorWidgetController controller) {
		this.controller = controller;
	}

}
