/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import java.util.List;

import org.cggh.chassis.generic.atom.rewrite.client.AtomEntry;
import org.cggh.chassis.generic.atom.rewrite.client.AtomLink;



/**
 * @author aliman
 *
 */
public interface SubmissionEntry extends AtomEntry {

	public List<AtomLink> getStudyLinks();
	public AtomLink getStudyLink(String href);
	public void setStudyLinks(List<String> hrefs);
	public void addStudyLink(String href);
	public void removeStudyLink(String href);
	public Submission getSubmission();

}
