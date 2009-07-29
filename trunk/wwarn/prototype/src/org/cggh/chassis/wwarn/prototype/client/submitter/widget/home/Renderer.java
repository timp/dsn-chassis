/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.home;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private RootPanel rootPanel;
	private Controller controller;
	private Logger log;

	Renderer(Controller controller) {
		this.controller = controller;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Renderer.class.getName());
	}


	void render() {
		log.enter("render");
		
		if (rootPanel == null) {

			log.trace("rootPanel is null, cannot render");

		}
		else {
		
			log.trace("rendering content");
			
			rootPanel.clear();
			rootPanel.add(new HTML("<h2>Submitter Home</h2>"));
			// TODO more

		}
		
		log.leave();
	}


	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}
	
	

}
