/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;

/**
 * @author aliman
 *
 */
public class AddInformationWidgetRenderer extends
		ChassisWidgetRenderer<AddInformationWidgetModel> {

	
	
	
	private Log log = LogFactory.getLog(AddInformationWidgetRenderer.class);
	
	
	
	private AddInformationWidgetView view;



	public void setView(AddInformationWidgetView view) {
		this.view = view;
	}

	
	
	
	@Override
	protected void renderUI() {
		
		canvas.clear();
		canvas.add(view.root);
		
		view.studyTitleLabel.setText("FOO");
		
	}

	

}
