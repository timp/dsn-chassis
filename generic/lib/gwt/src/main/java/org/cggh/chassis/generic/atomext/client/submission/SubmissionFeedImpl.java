/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.atom.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;




/**
 * @author aliman
 *
 */
public class SubmissionFeedImpl 
	extends AtomFeedImpl<SubmissionEntry, SubmissionFeed> 
	implements SubmissionFeed {

	
	
	/**
	 * @param feedElement
	 * @param factory
	 */
	protected SubmissionFeedImpl(Element feedElement, SubmissionFactory factory) {
		super(feedElement, factory);
	}

	
	
}
