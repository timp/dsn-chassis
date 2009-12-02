/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.coordinator.widget.allsubmissions;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	@SuppressWarnings("unused")
	private Controller controller;
	private RootPanel rootPanel;

	Renderer(Controller controller) {
		this.controller = controller;
	}

	void render() {
		rootPanel.clear();
		rootPanel.add(new HTML("<h2>All Submissions</h2>"));
		// TODO more
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

}
