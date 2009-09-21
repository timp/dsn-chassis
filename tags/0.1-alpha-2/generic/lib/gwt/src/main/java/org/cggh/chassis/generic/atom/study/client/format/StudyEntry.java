/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;

/**
 * @author aliman
 *
 */
public interface StudyEntry extends AtomEntry {

	public List<String> getModules();
	public void setModules(List<String> modules);
	public void addModule(String module);
	public void removeModule(String module);
	public Study getStudy();

}
