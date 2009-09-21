/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;

/**
 * @author aliman
 *
 */
public interface SubmissionFactory extends AtomFactory {

	public SubmissionEntry createSubmissionEntry();
	
}
