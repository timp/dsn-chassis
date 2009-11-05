/**
 * 
 */
package org.cggh.chassis.generic.atomext.shared;

import java.util.List;


/**
 * @author aliman
 *
 */
public interface HasModules {

	public List<String> getModules();
	public void setModules(List<String> modules);
	public void addModule(String module);
	public void removeModule(String module);
}
