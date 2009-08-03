/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.RoleNames;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


/**
 * @author aliman
 *
 */
public class CuratorPerspective extends FractalUIComponent implements Perspective {

	public static final String WIDGET_HOME = "home";
	public static final String WIDGET_MYSUBMISSIONS = "mysubmissions";
	public static final String WIDGET_ALLSUBMISSIONS = "allsubmissions";
	public static final String WIDGET_NEWSTANDARDDATADICTIONARY = "newstandarddatadictionary";
	public static final String WIDGET_ALLSTANDARDDATADICTIONARIES = "allstandarddatadictionaries";
	public static final String WIDGET_NEWRELEASECRITERIA = "newreleasecriteria";
	public static final String WIDGET_ALLRELEASECRITERIA = "allreleasecriteria";
	public static final String WIDGET_DELEGATENEWTASK = "delegatenewtask";
	public static final String WIDGET_MYDELEGATEDTASKS = "mydelegatedtasks";
	private static final String PROP_MAINWIDGETNAME = "mainWidgetName";
	
	
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

		log.trace("init model");
		model = new Model();

		log.trace("init controller");
		controller = new Controller(model, this); 

		log.trace("init renderer");
		renderer = new Renderer(this, controller, model);
		model.addListener(renderer);
		
		log.leave();
	}


	
	public String getRoleName() {
		return RoleNames.CURATOR;
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

	
	
	public void setIsCurrentPerspective(boolean b) {
		this.controller.setIsCurrentPerspective(b);
	}

	
	
	@Override
	protected Deferred<FractalUIComponent> syncState() {
		log.enter("syncState");

		if (this.stateKey == null) {

			log.trace("state key is null, override to defaults");
			this.controller.setDefault();

		}
		else {
			
			log.trace("set state as per key");
			this.syncMainWidget();

		}
		
		Deferred<FractalUIComponent> def = new Deferred<FractalUIComponent>();
		def.callback(this); // callback immediately
		
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

	
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}


}
