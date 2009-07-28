/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.home;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class Renderer implements ModelListener {

	private RootPanel rootPanel;
	private Controller controller;

	Renderer(Controller controller, RootPanel rootPanel) {
		this.controller = controller;
		this.rootPanel = rootPanel;
		this.initView();
	}

	private void initView() {
		rootPanel.clear();
		rootPanel.add(new HTML("<h2>Submitter Home</h2>"));
		// TODO more
	}
	
	

}
