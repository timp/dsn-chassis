/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;

/**
 * @author aliman
 *
 */
public interface SubmissionEntry extends AtomEntry {

	public List<String> getModules();
	public void setModules(List<String> modules);
	public void addModule(String module);
	public void removeModule(String module);

	public List<AtomLink> getStudyLinks();
	public AtomLink getStudyLink(String href);
	public void setStudyLinks(List<String> hrefs);
	public void addStudyLink(String href);
	public void removeStudyLink(String href);
	
}
