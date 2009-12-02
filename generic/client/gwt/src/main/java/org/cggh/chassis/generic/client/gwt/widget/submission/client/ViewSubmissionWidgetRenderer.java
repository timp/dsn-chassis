/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;




/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetRenderer extends
		AsyncWidgetRenderer<AtomCrudWidgetModel<SubmissionEntry>> {

	
	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidgetRenderer.class);

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		// TODO Auto-generated method stub
		
		this.mainPanel.add(h2("View Submission"));
		
		this.mainPanel.add(p("TODO"));

		log.leave();
	}

	
	
	
}
