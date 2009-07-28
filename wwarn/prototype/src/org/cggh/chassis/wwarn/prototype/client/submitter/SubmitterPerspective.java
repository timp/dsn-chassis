/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;

import com.google.gwt.json.client.JSONValue;


/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends HMVCComponent implements Perspective {

	public static final String WIDGET_HOME = "home";
	public static final String WIDGET_NEWSTUDY = "newstudy";
	public static final String WIDGET_MYSTUDIES = "mystudies";
	public static final String WIDGET_ALLSTUDIES = "allstudies";
	public static final String WIDGET_NEWSUBMISSION = "newsubmission";
	public static final String WIDGET_MYSUBMISSIONS = "mysubmissions";
	public static final String WIDGET_NEWDATADICTIONARY = "newdatadictionary";
	public static final String WIDGET_MYDATADICTIONARIES = "mydatadictionaries";
	public static final String WIDGET_ALLDATADICTIONARIES = "alldatadictionaries";

	private Logger log;
	private Model model;
	private Controller controller;
	private Renderer renderer;

	public SubmitterPerspective() {
		this.log = new GWTLogger();
		this.log.setCurrentClass(SubmitterPerspective.class.getName());
		this.init();
	}

	private void init() {
		log.enter("init");

		log.info("init model");
		this.model = new Model();

		log.info("init controller");
		this.controller = new Controller(model, this); 

		log.info("init renderer");
		this.renderer = new Renderer(controller);
		model.addListener(renderer);
		
		log.leave();
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

	public void setIsCurrentPerspective(boolean b) {
		this.controller.setIsCurrentPerspective(b);
	}

	@Override
	protected void syncState() {
		// TODO Auto-generated method stub
		
	}

}
