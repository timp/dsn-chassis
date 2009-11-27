/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;



/**
 * @author aliman
 *
 */
public interface SubmissionEntry extends AtomEntry {

	/**
	 * @param datasetEntryUrl
	 */
	void setDatasetLink(String datasetEntryUrl);


}
