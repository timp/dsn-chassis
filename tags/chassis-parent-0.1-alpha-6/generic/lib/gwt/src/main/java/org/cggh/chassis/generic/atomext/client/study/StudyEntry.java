/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;

/**
 * @author aliman
 *
 */
public interface StudyEntry extends AtomEntry {

	public Study getStudy();
	public List<DatasetLink> getDatasetLinks();
	
}
