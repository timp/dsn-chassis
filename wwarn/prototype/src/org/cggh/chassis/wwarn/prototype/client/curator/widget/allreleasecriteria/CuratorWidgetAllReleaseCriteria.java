/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.widget.allreleasecriteria;

import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class CuratorWidgetAllReleaseCriteria extends FractalUIComponent {

	
	
	private Model model;
	private Controller controller;
	private Renderer renderer;

	
	
	public CuratorWidgetAllReleaseCriteria() {
		super();
		this.log.setCurrentClass(CuratorWidgetAllReleaseCriteria.class.getName());
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
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent#syncState()
	 */
	@Override
	protected Deferred<FractalUIComponent> syncState() {
		// TODO Auto-generated method stub

		Deferred<FractalUIComponent> def = new Deferred<FractalUIComponent>();
		def.callback(this); // callback immediately
		return def;
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent#syncStateKey()
	 */
	@Override
	protected void syncStateKey() {
		// TODO Auto-generated method stub

	}



	@Override
	public void render() {
		renderer.render();
	}

	
	
}
