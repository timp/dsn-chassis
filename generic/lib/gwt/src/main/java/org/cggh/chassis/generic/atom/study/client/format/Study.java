/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format;

import java.util.List;

import org.cggh.chassis.generic.atom.chassis.base.client.format.ChassisFormatException;
import org.cggh.chassis.generic.atom.vanilla.client.format.ElementWrapper;

/**
 * @author aliman
 *
 */
public interface Study extends ElementWrapper {

	public List<String> getModules();
	public void setModules(List<String> modules);
	public void addModule(String module);
	public void removeModule(String module);
	
	public void setStartYear(int startYear);
	public int getStartYear();
	public void setEndYear(int endYear);
	public int getEndYear() throws ChassisFormatException;
	

}
