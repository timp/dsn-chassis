/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.shared;

import java.util.List;

import org.cggh.chassis.generic.xml.client.ElementWrapperImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class HasModulesImpl 
	extends ElementWrapperImpl 
	implements HasModules {

	
	
	
	/**
	 * @param e
	 */
	protected HasModulesImpl(Element e) {
		super(e);
	}

	
	
	
	public void addModule(String module) {
		ModulesHelper.addModule(this, module);
	}

	
	

	public List<String> getModules() {
		return ModulesHelper.getModules(this);
	}

	
	
	
	public void removeModule(String module) {
		ModulesHelper.removeModule(this, module);
	}

	
	
	
	public void setModules(List<String> modules) {
		ModulesHelper.setModules(this, modules);
	}

	
	
	
}
