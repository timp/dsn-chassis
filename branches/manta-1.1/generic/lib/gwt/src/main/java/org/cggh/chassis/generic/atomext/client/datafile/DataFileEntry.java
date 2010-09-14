/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;


/**
 * @author aliman
 *
 */
public interface DataFileEntry extends AtomEntry {

	public List<RevisionLink> getRevisionLinks();

	public List<DatasetLink> getDatasetLinks();
	
}
