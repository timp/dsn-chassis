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
public class AtomLinkImpl extends ElementWrapperImpl implements AtomLink {

	/**
	 * @param element
	 */
	protected AtomLinkImpl(Element e) {
		super(e);
		AtomUtils.verifyElement(Atom.ELEMENT_LINK, Atom.NSURI, e);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#getHref()
	 */
	public String getHref() {
		return this.element.getAttribute(Atom.ATTR_HREF);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#getHrefLang()
	 */
	public String getHrefLang() {
		return this.element.getAttribute(Atom.ATTR_HREFLANG);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#getLength()
	 */
	public String getLength() {
		return this.element.getAttribute(Atom.ATTR_LENGTH);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#getRel()
	 */
	public String getRel() {
		return this.element.getAttribute(Atom.ATTR_REL);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#getTitle()
	 */
	public String getTitle() {
		return this.element.getAttribute(Atom.ATTR_TITLE);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#getType()
	 */
	public String getType() {
		return this.element.getAttribute(Atom.ATTR_TYPE);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#setHref(java.lang.String)
	 */
	public void setHref(String href) {
		this.element.setAttribute(Atom.ATTR_HREF, href);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#setHrefLang(java.lang.String)
	 */
	public void setHrefLang(String hrefLang) {
		this.element.setAttribute(Atom.ATTR_HREFLANG, hrefLang);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#setLength(java.lang.String)
	 */
	public void setLength(String length) {
		this.element.setAttribute(Atom.ATTR_LENGTH, length);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#setRel(java.lang.String)
	 */
	public void setRel(String rel) {
		this.element.setAttribute(Atom.ATTR_REL, rel);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.element.setAttribute(Atom.ATTR_TITLE, title);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.element.setAttribute(Atom.ATTR_TYPE, type);
	}

}
