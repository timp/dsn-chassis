/**
 * $Id$
 */
package examples.atom.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class Application implements EntryPoint {

	
	private Controller controller;
	private Renderer renderer;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		this.controller = new Controller();
		this.renderer = new Renderer(this.controller);
	}

}
