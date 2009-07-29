/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.mystudies;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class Renderer implements ModelListener {

	private RootPanel rootPanel;
	private Controller controller;

	Renderer(Controller controller) {
		this.controller = controller;
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

	void render() {
		rootPanel.clear();
		rootPanel.add(new HTML("<h2>My Studies</h2>"));
		// TODO More
	}

}
