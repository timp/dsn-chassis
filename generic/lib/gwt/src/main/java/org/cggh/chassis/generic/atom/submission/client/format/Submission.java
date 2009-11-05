/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format;

import java.util.List;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.ElementWrapper;


/**
 * @author aliman
 *
 */
public interface Submission extends ElementWrapper {

	public List<String> getModules();
	public void setModules(List<String> modules);
	public void addModule(String module);
	public void removeModule(String module);

}
