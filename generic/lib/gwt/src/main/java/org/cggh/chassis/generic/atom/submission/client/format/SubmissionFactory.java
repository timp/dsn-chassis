/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public interface SubmissionFactory extends AtomFactory {

	public SubmissionEntry createSubmissionEntry();

	/**
	 * @param submissionElement
	 * @return
	 */
	public Submission createSubmission(Element submissionElement);
	
}
