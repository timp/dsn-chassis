/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

/**
 * @author aliman
 *
 */
public interface SubmissionFeed extends AtomFeed {

	public List<SubmissionEntry> getSubmissionEntries();
	
}
