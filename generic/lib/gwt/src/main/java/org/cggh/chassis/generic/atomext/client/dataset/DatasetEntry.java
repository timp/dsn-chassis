/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;

/**
 * @author aliman
 *
 */
public interface DatasetEntry extends AtomEntry {

	public List<StudyLink> getStudyLinks();
	public List<DataFileLink> getDataFileLinks();

}
