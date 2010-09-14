/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import java.util.List;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomLinkImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class StudyLinkImpl extends AtomLinkImpl implements StudyLink {

	/**
	 * @param e
	 */
	public StudyLinkImpl(Element e) {
		super(e);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.StudyLink#getEntry()
	 */
	public StudyEntry getEntry() {

		StudyEntry studyEntry = null;
		
		List<Element> nestedEntries = XMLNS.getElementsByTagNameNS(this.element, Atom.ELEMENT_ENTRY, Atom.NSURI);

		if (nestedEntries.size() > 0) {
			StudyFactory factory = new StudyFactory();
			studyEntry = factory.createEntry(nestedEntries.get(0));
		}
		
		return studyEntry;
	}

}
