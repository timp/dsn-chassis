package org.cggh.chassis.wwarn.prototype.client.submitter.widget.home;

import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;

import com.google.gwt.user.client.ui.RootPanel;

public class SubmitterWidgetHome extends FractalUIComponent {

	
	
	private Model model;
	private Controller controller;
	private Renderer renderer;
	private RootPanel rootPanel;

	public SubmitterWidgetHome() {
		super();
		this.log.setCurrentClass(SubmitterWidgetHome.class.getName());
		this.initialise();
	}
	
	
	
	
	void initialise() {
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

	
	
	@Override
	public void setRootPanel(RootPanel root) {
		super.setRootPanel(root);
		renderer.setRootPanel(root);
	}



	@Override
	protected Deferred syncState() {

		// TODO Auto-generated method stub
		Deferred def = new Deferred();
		def.callback(null); // callback immediately
		return def;
	}

	
	
	@Override
	protected void syncStateKey() {
		// TODO Auto-generated method stub

	}




	@Override
	public void render() {
		log.enter("render");
		
		if (renderer == null) {
			log.trace("renderer is null");
		}
		else {
			
			log.trace("calling renderer.render");
			renderer.render();

		}
		
		
		log.leave();
	}

}
