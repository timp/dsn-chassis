/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomComponentFactory {
	
	
	
	public AtomAuthor createAuthor() {
		Element e = XMLNS.createElementNS(Atom.ELEMENT_AUTHOR, Atom.PREFIX, Atom.NSURI);
		return new AtomAuthorImpl(e);
	}

	
	
	
	public AtomAuthor createAuthor(Element authorElement) {
		return new AtomAuthorImpl(authorElement);
	}




	public AtomLink createLink() {
		Element e = XMLNS.createElementNS(Atom.ELEMENT_LINK, Atom.PREFIX, Atom.NSURI);
		return new AtomLinkImpl(e);
	}
	
	
	

	public AtomLink createLink(String href, String rel) {
		AtomLink link = this.createLink();
		link.setHref(href);
		link.setRel(rel);
		return link;
	}
	
	
	

	public AtomLink createLink(Element linkElement) {
		return new AtomLinkImpl(linkElement);
	}
	
	
	

	public AtomCategory createCategory() {
		Element e = XMLNS.createElementNS(Atom.ELEMENT_CATEGORY, Atom.PREFIX, Atom.NSURI);
		return new AtomCategoryImpl(e);
	}

	
	
	
	public AtomCategory createCategory(Element categoryElement) {
		return new AtomCategoryImpl(categoryElement);
	}




}
