/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;


/**
 * @author aliman
 *
 */
public class SubmitterPerspective extends FractalUIComponent implements Perspective {

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

		log.trace("init model");
		this.model = new Model();

		log.trace("init controller");
		this.controller = new Controller(model, this); 

		log.trace("init renderer");
		this.renderer = new Renderer(this, controller, model);
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
		
		log.trace("sync main widget: "+this.model.getMainWidgetName());
		this.stateKey = new JSONObject();
		JSONString value = new JSONString(this.model.getMainWidgetName());
		this.stateKey.put(PROP_MAINWIDGETNAME, value);
		
		log.leave();
	}

	@Override
	protected Deferred syncState() {
		log.enter("syncState");

		if (this.stateKey == null) {

			log.trace("state key is null, override to defaults");
			this.controller.setDefault();

		}
		else {
			
			log.trace("set state as per key");
			this.syncMainWidget();

		}
		
		Deferred def = new Deferred();
		def.callback(null); // callback immediately
		
		log.leave();
		return def;
	}

	private void syncMainWidget() {
		log.enter("syncMainWidget");
		
		if (stateKey.containsKey(PROP_MAINWIDGETNAME)) {
			
			JSONString widgetName = (JSONString) stateKey.get(PROP_MAINWIDGETNAME);
			log.trace("found main widget name: "+widgetName.stringValue());
			this.controller.setMainWidget(widgetName.stringValue(), false);

		}
		
		log.leave();
	}

}
