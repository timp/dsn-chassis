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
public class AssignCuratorWidgetRenderer 
	extends AsyncWidgetRenderer<AssignCuratorWidgetModel> {

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		// TODO Auto-generated method stub

		this.canvas.add(pWidget("Please select a curator from the list below..."));
	}

}
