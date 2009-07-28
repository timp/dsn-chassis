/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy;

import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;

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

		log.info("init model");
		model = new Model();

		log.info("init controller");
		controller = new Controller(model, this); 

		log.info("init renderer");
		renderer = new Renderer(controller, rootPanel);
		model.addListener(renderer);
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent#syncState()
	 */
	@Override
	protected void syncState() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent#syncStateKey()
	 */
	@Override
	protected void syncStateKey() {
		// TODO Auto-generated method stub

	}

}
