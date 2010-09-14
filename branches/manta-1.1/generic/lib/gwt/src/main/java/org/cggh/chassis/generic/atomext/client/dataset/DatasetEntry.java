/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileLink;
import org.cggh.chassis.generic.atomext.client.study.StudyLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionLink;

/**
 * @author aliman
 *
 */
public interface DatasetEntry extends AtomEntry {

	public List<StudyLink> getStudyLinks();
	public List<DataFileLink> getDataFileLinks();
	public void addStudyLink(String href);
	public void addStudyLink(int index, String href);
	public void removeStudyLink(String href);
	public void removeStudyLink(int index);
	public void addDataFileLink(String value);
	public void addDataFileLink(int index, String value);
	public void removeDataFileLink(String value);
	public void removeDataFileLink(int index);
	public SubmissionLink getSubmissionLink();

}
