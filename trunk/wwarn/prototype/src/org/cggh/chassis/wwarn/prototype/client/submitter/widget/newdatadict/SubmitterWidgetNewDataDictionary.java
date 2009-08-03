/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.newdatadict;

import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.twisted.client.Function;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allreleasecriteria.CuratorWidgetAllReleaseCriteria;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SubmitterWidgetNewDataDictionary extends FractalUIComponent {

	
	
	public static final Function<Object[],FractalUIComponent> creator = new Function<Object[],FractalUIComponent>() {
		
		public FractalUIComponent apply(Object[] args) {
			return new SubmitterWidgetNewDataDictionary();
		}
		
	};
	
	
	
	private Model model;
	private Controller controller;
	private Renderer renderer;

	
	
	public SubmitterWidgetNewDataDictionary() {
		super();
		this.log.setCurrentClass(SubmitterWidgetNewDataDictionary.class.getName());
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
