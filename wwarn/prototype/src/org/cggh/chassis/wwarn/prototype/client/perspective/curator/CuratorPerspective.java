/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.perspective.curator;

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
public class CuratorPerspective extends HMVCComponent implements Perspective {

	public static final String WIDGET_HOME = "home";
	public static final String WIDGET_MYSUBMISSIONS = "mysubmissions";
	public static final String WIDGET_ALLSUBMISSIONS = "allsubmissions";
	public static final String WIDGET_NEWSTANDARDDATADICTIONARY = "newstandarddatadictionary";
	public static final String WIDGET_ALLSTANDARDDATADICTIONARIES = "allstandarddatadictionaries";
	public static final String WIDGET_NEWRELEASECRITERIA = "newreleasecriteria";
	public static final String WIDGET_ALLRELEASECRITERIA = "allreleasecriteria";
	public static final String WIDGET_DELEGATENEWTASK = "delegatenewtask";
	public static final String WIDGET_MYDELEGATEDTASKS = "mydelegatedtasks";
	
	
	private Logger log;
	private Model model;
	private Controller controller;
	private Renderer renderer;

	public CuratorPerspective() {
		this.log = new GWTLogger();
		this.log.setCurrentClass(CuratorPerspective.class.getName());
		this.init();
	}
	
	private void init() {
		log.enter("init");

		log.info("init model");
		model = new Model();

		log.info("init controller");
		controller = new Controller(model, this); 

		log.info("init renderer");
		renderer = new Renderer(controller);
		model.addListener(renderer);
		
		log.leave();
	}


	public String getRoleName() {
		return RoleNames.CURATOR;
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
