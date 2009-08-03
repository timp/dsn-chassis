/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.base.perspective;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * @author aliman
 *
 */
public abstract class BasePerspective extends FractalUIComponent implements Perspective {

	
	
	protected Logger log;
	protected BasePerspectiveModel model;
	protected BasePerspectiveController controller;

	
	
	protected static final String PROP_MAINWIDGETNAME = "mainWidgetName";
	public static final String WIDGET_HOME = "home";

	
	
	public BasePerspective() {
		this.log = new GWTLogger();
	}
	
	
	public abstract String getRoleName();


	
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

	
	
	public void setIsCurrentPerspective(boolean b) {
		this.controller.setIsCurrentPerspective(b);
	}

	
	


}
