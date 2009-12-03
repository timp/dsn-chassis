/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.allstudies;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class Renderer implements ModelListener {

	private RootPanel rootPanel;
	@SuppressWarnings("unused")
	private Controller controller;

	Renderer(Controller controller) {
		this.controller = controller;
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

	void render() {
		rootPanel.clear();
		rootPanel.add(new HTML("<h2>All Studies</h2>"));
		// TODO More
	}

}
