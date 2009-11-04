/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client.submission;

import java.util.List;

import org.cggh.chassis.generic.atom.rewrite.client.ElementWrapper;

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
