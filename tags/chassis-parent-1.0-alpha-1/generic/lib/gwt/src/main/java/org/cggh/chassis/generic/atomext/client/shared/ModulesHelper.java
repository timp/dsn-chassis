/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.shared;

import java.util.List;

import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.ElementWrapper;
import org.cggh.chassis.generic.xml.client.XMLNS;

/**
 * @author aliman
 *
 */
public class ModulesHelper {
	
	
	
	
	private static Log log = LogFactory.getLog(ModulesHelper.class);

	
	
	
	public static List<String> getModules(ElementWrapper parent) {
		log.enter("getModules");

		List<String> modules = XMLNS.getElementsSimpleContentsByTagNameNS(parent.getElement(), Chassis.Element.MODULE, Chassis.NSURI);
		
		log.leave();
		return modules;
	}
	
	
	
	
	
	public static void setModules(ElementWrapper parent, List<String> modules) {
		log.enter("setModules");
		
		XMLNS.setElementsSimpleContentsByTagNameNS(parent.getElement(), Chassis.Element.MODULE, Chassis.PREFIX, Chassis.NSURI, modules);
		
		log.leave();
	}
	
	
	
	
	
	public static void addModule(ElementWrapper parent, String module) {
		log.enter("addModule");
		
		List<String> modules = getModules(parent);
		modules.add(module);
		setModules(parent, modules);
		
		log.leave();
	}

	
	
	
	public static void removeModule(ElementWrapper parent, String module) {
		log.enter("removeModule");
		
		List<String> modules = getModules(parent);
		modules.remove(module);
		setModules(parent, modules);
		
		log.leave();
	}
	
	
	

}
