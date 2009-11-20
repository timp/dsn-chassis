/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import java.util.List;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomLinkImpl;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFactory;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DatasetLinkImpl extends AtomLinkImpl implements DatasetLink {

	/**
	 * @param e
	 */
	protected DatasetLinkImpl(Element e) {
		super(e);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.study.DatasetLink#getEntry()
	 */
	public DatasetEntry getEntry() {
		DatasetEntry entry = null;
		
		List<Element> nestedEntries = XMLNS.getElementsByTagNameNS(this.element, Atom.ELEMENT_ENTRY, Atom.NSURI);

		if (nestedEntries.size() > 0) {
			DatasetFactory factory = new DatasetFactory();
			entry = factory.createEntry(nestedEntries.get(0));
		}
		
		return entry;
	}

}
