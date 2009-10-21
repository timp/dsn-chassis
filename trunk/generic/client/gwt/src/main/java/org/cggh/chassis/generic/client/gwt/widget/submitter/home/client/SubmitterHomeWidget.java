/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submitter.home.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author aliman
 *
 */
public class SubmitterHomeWidget extends Composite {
	

	
	
	private static final String STYLENAME_BASE = "chassis-submitterHome";
	private Panel canvas = new FlowPanel();
	
	
	
	
	public SubmitterHomeWidget() {
		this.canvas.addStyleName(STYLENAME_BASE);
		this.canvas.add(new HTML("<h2>Submitter Home</h2>"));
		this.initWidget(this.canvas);
	}
	
	
	
}
