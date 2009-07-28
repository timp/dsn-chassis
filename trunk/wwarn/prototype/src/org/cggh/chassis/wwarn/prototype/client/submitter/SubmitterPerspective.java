/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;


/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends HMVCComponent implements Perspective {

	private GWTLogger log;

	public SubmitterPerspective() {
		this.log = new GWTLogger();
		this.log.setCurrentClass(SubmitterPerspective.class.getName());
		this.init();
	}

	private void init() {
		log.setCurrentMethod("init");
		log.info("begin");

		log.info("init model");
		Model model = new Model();

		log.info("init controller");
		Controller controller = new Controller(model, this); 

		log.info("init renderer");
		Renderer renderer = new Renderer(controller);
		model.addListener(renderer);
		
		log.info("complete initialisation");
		controller.init();
		
		log.info("return");

	}


	public final String getRoleName() {
		return RoleNames.SUBMITTER;
	}


	@Override
	public void captureHistoryEvent(JSONValue stateToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void syncStateKey() {
		// TODO Auto-generated method stub
		
	}

	public void setIsCurrent(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void syncState() {
		// TODO Auto-generated method stub
		
	}

}
