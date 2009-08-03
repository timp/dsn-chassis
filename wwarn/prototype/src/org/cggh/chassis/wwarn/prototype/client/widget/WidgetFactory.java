/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.widget;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Function;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;


/**
 * @author aliman
 *
 */
public class WidgetFactory {

	private static Map<String,Function<Object[],FractalUIComponent>> register = new HashMap<String,Function<Object[],FractalUIComponent>>();
	private static Logger log = new GWTLogger();
	
	static {
		log.setCurrentClass(WidgetFactory.class.getName());
		log.trace("===== static code executed =====");
	}
	
	public static FractalUIComponent create(String componentName, Object[] args) {
		log.enter("create");
		
		FractalUIComponent ret = null;

		// guard condition
		if (componentName == null) {
			return ret;
		}

		
		if (register.containsKey(componentName)) {
			Function<Object[],FractalUIComponent> creator = register.get(componentName);
			ret = creator.apply(args);
		}
		
		log.leave();
		return ret;
	}
	
	public static void register(String componentName, Function<Object[], FractalUIComponent> creator) {
		log.enter("register");
		log.trace("================================ componentName: "+componentName);
		
		register.put(componentName, creator);
		
		log.leave();
	}
	
}
