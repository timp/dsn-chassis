/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.xml.client.XML;

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
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#getEmail()
	 */
	public String getEmail() {
		return XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_EMAIL);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#getName()
	 */
	public String getName() {
		return XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_NAME);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#getURI()
	 */
	public String getURI() {
		return XML.getElementSimpleContentByTagName(element, Atom.ELEMENT_URI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#setEmail(java.lang.String)
	 */
	public void setEmail(String email) {
		XML.setElementSimpleContentByTagName(element, Atom.ELEMENT_EMAIL, email);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#setName(java.lang.String)
	 */
	public void setName(String name) {
		XML.setElementSimpleContentByTagName(element, Atom.ELEMENT_NAME, name);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor#setURI(java.lang.String)
	 */
	public void setURI(String uri) {
		XML.setElementSimpleContentByTagName(element, Atom.ELEMENT_URI, uri);
	}

}
