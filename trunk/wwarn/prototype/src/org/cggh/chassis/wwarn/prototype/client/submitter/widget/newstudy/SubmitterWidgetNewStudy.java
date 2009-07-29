/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy;

import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.twisted.Deferred;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SubmitterWidgetNewStudy extends HMVCComponent {

	private Model model;
	private Controller controller;
	private Renderer renderer;
	private RootPanel rootPanel;

	public SubmitterWidgetNewStudy() {
		super();
		this.log.setCurrentClass(SubmitterWidgetNewStudy.class.getName());
	}
	
	
	
	public void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}
	
	
	
	public void initialise() {
		log.enter("initialise");

		log.trace("init model");
		model = new Model();

		log.trace("init controller");
		controller = new Controller(model, this); 

		log.trace("init renderer");
		renderer = new Renderer(controller, rootPanel);
		model.addListener(renderer);
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent#syncState()
	 */
	@Override
	protected Deferred syncState() {
		// TODO Auto-generated method stub

		Deferred def = new Deferred();
		def.callback(null); // callback immediately
		return def;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent#syncStateKey()
	 */
	@Override
	protected void syncStateKey() {
		// TODO Auto-generated method stub

	}

}
