/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client;

import org.cggh.chassis.generic.xml.client.ElementWrapperImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomAuthorImpl extends ElementWrapperImpl implements AtomAuthor {

	
	
	/**
	 * @param e
	 */
	protected AtomAuthorImpl(Element e) {
		super(e);
		AtomUtils.verifyElement(Atom.ELEMENT_AUTHOR, Atom.NSURI, e);
	}
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#getEmail()
	 */
	public String getEmail() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_EMAIL, Atom.NSURI);
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#getName()
	 */
	public String getName() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_NAME, Atom.NSURI);
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#getURI()
	 */
	public String getURI() {
		return XMLNS.getFirstElementSimpleContentByTagNameNS(element, Atom.ELEMENT_URI, Atom.NSURI);
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_EMAIL, Atom.PREFIX, Atom.NSURI, email);
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#setName(java.lang.String)
	 */
	public void setName(String name) {
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_NAME, Atom.PREFIX, Atom.NSURI, name);
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#setURI(java.lang.String)
	 */
	public void setURI(String uri) {
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Atom.ELEMENT_URI, Atom.PREFIX, Atom.NSURI, uri);
	}
	
	

}
