/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import java.util.List;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atom.client.AtomLinkImpl;
import org.cggh.chassis.generic.atomext.client.media.MediaEntry;
import org.cggh.chassis.generic.atomext.client.media.MediaFactory;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class RevisionLinkImpl 
	extends AtomLinkImpl 
	implements RevisionLink {

	
	
	
	private Log log = LogFactory.getLog(RevisionLinkImpl.class);

	
	
	
	/**
	 * @param e
	 */
	protected RevisionLinkImpl(Element e) {
		super(e);
	}




	/**
	 * @param l
	 * @return
	 */
	public static RevisionLink as(AtomLink l) {
		return new RevisionLinkImpl(l.getElement());
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.datafile.RevisionLink#getEntry()
	 */
	public MediaEntry getEntry() {
		log.enter("getEntry");

		MediaEntry mediaEntry = null;
		
		List<Element> nestedEntries = XMLNS.getElementsByTagNameNS(this.element, Atom.ELEMENT_ENTRY, Atom.NSURI);

		if (nestedEntries.size() > 0) {
			MediaFactory factory = new MediaFactory();
			mediaEntry = factory.createEntry(nestedEntries.get(0));
		}
		
		log.leave();
		return mediaEntry;
	}

}
