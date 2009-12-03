/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import java.util.List;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomLinkImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DataFileLinkImpl extends AtomLinkImpl implements DataFileLink {


	
	
	
	/**
	 * @param e
	 */
	public DataFileLinkImpl(Element e) {
		super(e);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DataFileLink#getEntry()
	 */
	public DataFileEntry getEntry() {
		DataFileEntry dataFileEntry = null;
		
		List<Element> nestedEntries = XMLNS.getElementsByTagNameNS(this.element, Atom.ELEMENT_ENTRY, Atom.NSURI);

		if (nestedEntries.size() > 0) {
			DataFileFactory factory = new DataFileFactory();
			dataFileEntry = factory.createEntry(nestedEntries.get(0));
		}
		
		return dataFileEntry;
	}

	
	
	
}
