/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class Renderer implements ModelListener {

	private Controller controller;
	private RootPanel rootPanel;

	Renderer(Controller controller, RootPanel rootPanel) {
		this.controller = controller;
		this.rootPanel = rootPanel;
		this.initView();
	}

	private void initView() {
		rootPanel.clear();
		rootPanel.add(new HTML("<h2>New Study</h2>"));
		// TODO more
	}

}
