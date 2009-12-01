/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;



/**
 * @author aliman
 *
 */
public interface SubmissionEntry extends AtomEntry {

	/**
	 * @param datasetEntryUrl
	 */
	public void setDatasetLink(String datasetEntryUrl);

	public DatasetLink getDatasetLink();
	

}
