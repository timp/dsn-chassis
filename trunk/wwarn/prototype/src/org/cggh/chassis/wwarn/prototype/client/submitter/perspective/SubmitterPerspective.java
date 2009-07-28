/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
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
	public static final String ELEMENTID_APPCONTENT = "appcontent";
	private static final String PROP_MAINWIDGETNAME = "mainWidgetName";

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
		this.renderer = new Renderer(this, controller);
		model.addListener(renderer);
		
		log.leave();
	}


	public final String getRoleName() {
		return RoleNames.SUBMITTER;
	}


	public void setIsCurrentPerspective(boolean b) {
		this.controller.setIsCurrentPerspective(b);
	}
	


	@Override
	protected void syncStateKey() {
		log.enter("syncStateKey");
		
		log.info("sync main widget: "+this.model.getMainWidgetName());
		this.stateKey = new JSONObject();
		JSONString value = new JSONString(this.model.getMainWidgetName());
		this.stateKey.put(PROP_MAINWIDGETNAME, value);
		
		log.leave();
	}

	@Override
	protected void syncState() {
		log.enter("syncState");

		if (this.stateKey == null) {

			log.info("state key is null, override to defaults");
			this.controller.setDefault();

		}
		else {
			
			log.info("set state as per key");
			this.syncMainWidget();

		}
		
		log.leave();
	}

	private void syncMainWidget() {
		log.enter("syncMainWidget");
		
		if (stateKey.containsKey(PROP_MAINWIDGETNAME)) {
			
			JSONString widgetName = (JSONString) stateKey.get(PROP_MAINWIDGETNAME);
			log.info("found main widget name: "+widgetName.stringValue());
			this.controller.setMainWidget(widgetName.stringValue(), false);

		}
		
		log.leave();
	}

}
