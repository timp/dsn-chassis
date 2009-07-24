/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * @author aliman
 *
 */
public class Application implements EntryPoint {

	static final String TOKEN_UNAUTHORISED = "unauthorised";
	static final String TOKEN_NOTFOUND = "notfound";

	private void log(String message, String context) {
		String output = Application.class.getName() + " :: " + context + " :: " + message;
		GWT.log(output, null);
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		String context = "onModuleLoad";
		
		log("begin", context);

		log("init model", context);
		Model model = new Model();

		log("init controller", context);
		Controller controller = new Controller(model); 

		log("init renderer", context);
		Renderer renderer = new Renderer(controller);
		model.addListener(renderer);
		
		log("complete initialisation", context);
		controller.init();
		
		log("end", context);
	}

}
