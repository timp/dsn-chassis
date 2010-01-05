/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atomext.shared.Chassis;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmissionFactory 
	extends AtomFactory<SubmissionEntry, SubmissionFeed> {
	
	
	
	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\""+Chassis.SCHEME_TYPES+"\" term=\""+Chassis.Type.SUBMISSION+"\"/>" +
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
