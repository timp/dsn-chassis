/**
 * @author timp
 * @since 14 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.review;

import java.util.List;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomLinkImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

public class ReviewLinkImpl extends AtomLinkImpl implements ReviewLink {

	public ReviewLinkImpl(Element e) {
		super(e);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.review.ReviewLink#getEntry()
	 */
	public ReviewEntry getEntry() {
		ReviewEntry entry = null;
		
		List<Element> nestedEntries = XMLNS.getElementsByTagNameNS(this.element, Atom.ELEMENT_ENTRY, Atom.NSURI);

		if (nestedEntries.size() > 0) {
			ReviewFactory factory = new ReviewFactory();
			entry = factory.createEntry(nestedEntries.get(0));
		}
		
		return entry;
	}

}
