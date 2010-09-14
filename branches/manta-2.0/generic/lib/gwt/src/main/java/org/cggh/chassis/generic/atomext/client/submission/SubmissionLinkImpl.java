/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import java.util.List;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomLinkImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmissionLinkImpl extends AtomLinkImpl implements SubmissionLink {

	/**
	 * @param e
	 */
	public SubmissionLinkImpl(Element e) {
		super(e);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.submission.SubmissionLink#getEntry()
	 */
	public SubmissionEntry getEntry() {
		SubmissionEntry entry = null;
		
		List<Element> nestedEntries = XMLNS.getElementsByTagNameNS(this.element, Atom.ELEMENT_ENTRY, Atom.NSURI);

		if (nestedEntries.size() > 0) {
			SubmissionFactory factory = new SubmissionFactory();
			entry = factory.createEntry(nestedEntries.get(0));
		}
		
		return entry;
	}

}
