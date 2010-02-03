/**
 * 
 */
package org.cggh.chassis.generic.atom.client;


import org.cggh.chassis.generic.xml.client.ElementWrapperImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomCategoryImpl extends ElementWrapperImpl implements AtomCategory {

	/**
	 * @param e
	 */
	protected AtomCategoryImpl(Element e) {
		super(e);
		AtomUtils.verifyElement(Atom.ELEMENT_CATEGORY, Atom.NSURI, e);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory#getLabel()
	 */
	public String getLabel() {
		return element.getAttribute(Atom.ATTR_LABEL);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory#getScheme()
	 */
	public String getScheme() {
		return element.getAttribute(Atom.ATTR_SCHEME);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory#getTerm()
	 */
	public String getTerm() {
		return element.getAttribute(Atom.ATTR_TERM);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		element.setAttribute(Atom.ATTR_LABEL, label);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory#setScheme(java.lang.String)
	 */
	public void setScheme(String scheme) {
		element.setAttribute(Atom.ATTR_SCHEME, scheme);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory#setTerm(java.lang.String)
	 */
	public void setTerm(String term) {
		element.setAttribute(Atom.ATTR_TERM, term);
	}

}
