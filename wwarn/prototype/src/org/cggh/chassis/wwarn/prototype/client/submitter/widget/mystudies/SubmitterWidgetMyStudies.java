/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.mystudies;

import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SubmitterWidgetMyStudies extends FractalUIComponent {

	
	
	
	private Model model;
	private Controller controller;
	private Renderer renderer;
	
	
	
	
	public SubmitterWidgetMyStudies() {
		super();
		this.log.setCurrentClass(SubmitterWidgetMyStudies.class.getName());
		this.initialise();
	}

	
	
	
	@Override
	public void setRootPanel(RootPanel root) {
		super.setRootPanel(root);
		renderer.setRootPanel(root);
	}
	
	
	
	
	public void initialise() {
		log.enter("initialise");

		log.trace("init model");
		model = new Model();

		log.trace("init controller");
		controller = new Controller(model, this); 

		log.trace("init renderer");
		renderer = new Renderer(controller);
		model.addListener(renderer);
		
		log.leave();
	}

	
	



	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent#render()
	 */
	@Override
	public void render() {
		renderer.render();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent#syncState()
	 */
	@Override
	protected Deferred syncState() {
		// TODO Auto-generated method stub

		Deferred def = new Deferred();
		def.callback(null); // callback immediately
		return def;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent#syncStateKey()
	 */
	@Override
	protected void syncStateKey() {
		// TODO Auto-generated method stub

	}

	
	
	
	
}
