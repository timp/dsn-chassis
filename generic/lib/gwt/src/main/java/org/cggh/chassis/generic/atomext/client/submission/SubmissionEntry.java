/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;
import org.cggh.chassis.generic.atomext.client.review.ReviewLink;



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

	public Curator getCurator();

	public List<ReviewLink> getReviewLinks();
	
    /** Submissions currently only get one review */
	public ReviewLink getReviewLink();
	

}
