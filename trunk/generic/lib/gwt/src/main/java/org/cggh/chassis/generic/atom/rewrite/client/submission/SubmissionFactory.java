/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client.submission;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.rewrite.client.Atom;
import org.cggh.chassis.generic.atom.rewrite.client.AtomFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmissionFactory 
	extends AtomFactory<SubmissionEntry, SubmissionFeed> {
	
	
	
	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\"http://www.cggh.org/2009/chassis/type/\" term=\"Submission\"/>" +
			"<atom:content type=\"application/xml\">" +
				"<chassis:submission xmlns:chassis=\""+Chassis.NSURI+"\"></chassis:submission>" +
			"</atom:content>" +
		"</atom:entry>";
	
	
	
	
	public static String TEMPLATE_FEED = 
		"<atom:feed xmlns:atom=\""+Atom.NSURI+"\"/>";

	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#createEntry(com.google.gwt.xml.client.Element)
	 */
	@Override
	public SubmissionEntry createEntry(Element entryElement) {
		return new SubmissionEntryImpl(entryElement, this);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#createFeed(com.google.gwt.xml.client.Element)
	 */
	@Override
	public SubmissionFeed createFeed(Element feedElement) {
		return new SubmissionFeedImpl(feedElement, this);
	}

	
	
	
	public Submission createSubmission(Element submissionElement) {
		return new SubmissionImpl(submissionElement);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#getEntryTemplate()
	 */
	@Override
	public String getEntryTemplate() {
		return TEMPLATE_ENTRY;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#getFeedTemplate()
	 */
	@Override
	public String getFeedTemplate() {
		return TEMPLATE_FEED;
	}
	
	
	

}
