/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.study;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.twisted.client.Function;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SubmitterWidgetStudy extends FractalUIComponent {

	
	
	
	public static final Function<Object[],FractalUIComponent> creator = new Function<Object[],FractalUIComponent>() {
		
		public FractalUIComponent apply(Object[] args) {
			return new SubmitterWidgetStudy();
		}
		
	};
	
	
	
	private Model model;
	private Controller controller;
	private Renderer renderer;
	
	
	
	
	public SubmitterWidgetStudy() {
		super();
		this.log.setCurrentClass(SubmitterWidgetStudy.class.getName());
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
	protected Deferred<FractalUIComponent> syncState() {
		// TODO Auto-generated method stub

		Deferred<FractalUIComponent> def = new Deferred<FractalUIComponent>();
		def.callback(this); // callback immediately
		return def;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent#syncStateKey()
	 */
	@Override
	protected void syncStateKey() {
		// TODO Auto-generated method stub

	}




	public void setMessage(String message) {
		controller.setMessage(message);
		
	}




	public void setStudyEntry(StudyEntry study) {
		controller.setStudyEntry(study);
	}

	
	
	
	
}
