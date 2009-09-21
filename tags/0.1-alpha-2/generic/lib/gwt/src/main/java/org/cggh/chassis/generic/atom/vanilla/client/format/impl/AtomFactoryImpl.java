/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format.impl;

import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;

/**
 * @author aliman
 *
 */
public class AtomFactoryImpl implements AtomFactory {

	private static String TEMPLATE_LINK = "<link xmlns=\""+Atom.NSURI+"\"/>";
	private static String TEMPLATE_AUTHOR = "<author xmlns=\""+Atom.NSURI+"\"/>";
	private static String TEMPLATE_CATEGORY = "<category xmlns=\""+Atom.NSURI+"\"/>";
	private static String TEMPLATE_ENTRY = "<entry xmlns=\""+Atom.NSURI+"\"/>";

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createAuthor()
	 */
	public AtomAuthor createAuthor() {
		Document d = XMLParser.parse(TEMPLATE_AUTHOR);
		return new AtomAuthorImpl(d.getDocumentElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createAuthor(com.google.gwt.xml.client.Element)
	 */
	public AtomAuthor createAuthor(Element authorElement) {

		// guard conditions
		
		if (!authorElement.getTagName().equals(Atom.ELEMENT_AUTHOR)) {
			throw new AtomFormatException("bad tag name, expected: "+Atom.ELEMENT_AUTHOR+"; found: "+authorElement.getTagName());
		}

		if (authorElement.getNamespaceURI() == null || !authorElement.getNamespaceURI().equals(Atom.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Atom.NSURI+"; found: "+authorElement.getNamespaceURI());
		}

		return new AtomAuthorImpl(authorElement);

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry()
	 */
	public AtomEntry createEntry() {
		Document d = XMLParser.parse(TEMPLATE_ENTRY);
		return new AtomEntryImpl(d.getDocumentElement(), this);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(java.lang.String)
	 */
	public AtomEntry createEntry(String entryDocument) {
		try {
			Document d = XMLParser.parse(entryDocument);
			return createEntry(d.getDocumentElement());
		} catch (DOMParseException d) {
			throw new AtomFormatException("parse exception: "+d.getLocalizedMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(com.google.gwt.xml.client.Element)
	 */
	public AtomEntry createEntry(Element entryElement) {
		
		// guard conditions
		
		if (!entryElement.getTagName().equals(Atom.ELEMENT_ENTRY)) {
			throw new AtomFormatException("bad tag name, expected: "+Atom.ELEMENT_ENTRY+"; found: "+entryElement.getTagName());
		}

		if (entryElement.getNamespaceURI() == null || !entryElement.getNamespaceURI().equals(Atom.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Atom.NSURI+"; found: "+entryElement.getNamespaceURI());
		}
		
		return new AtomEntryImpl(entryElement, this);
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createFeed(java.lang.String)
	 */
	public AtomFeed createFeed(String feedDocument) {

		Document d;
		Element feedElement;
		
		// guard conditions
		
		try {
			d = XMLParser.parse(feedDocument);
		} catch (DOMParseException e) {
			throw new AtomFormatException("parse exception: "+e.getLocalizedMessage());
		}

		feedElement = d.getDocumentElement();
			
		if (!XML.getLocalName(feedElement).equals(Atom.ELEMENT_FEED)) {
			throw new AtomFormatException("bad local tag name, expected: "+Atom.ELEMENT_FEED+"; found: "+feedElement.getTagName());
		}

		if (feedElement.getNamespaceURI() == null || !feedElement.getNamespaceURI().equals(Atom.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Atom.NSURI+"; found: "+feedElement.getNamespaceURI());
		}
		
		return new AtomFeedImpl(d.getDocumentElement(), this);

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createLink()
	 */
	public AtomLink createLink() {
		Document d = XMLParser.parse(TEMPLATE_LINK);
		return new AtomLinkImpl(d.getDocumentElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createLink(com.google.gwt.xml.client.Element)
	 */
	public AtomLink createLink(Element linkElement) {
		
		// guard conditions
		
		if (!linkElement.getTagName().equals(Atom.ELEMENT_LINK)) {
			throw new AtomFormatException("bad tag name, expected: "+Atom.ELEMENT_LINK+"; found: "+linkElement.getTagName());
		}

		if (linkElement.getNamespaceURI() == null || !linkElement.getNamespaceURI().equals(Atom.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Atom.NSURI+"; found: "+linkElement.getNamespaceURI());
		}

		return new AtomLinkImpl(linkElement);

	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createCategory()
	 */
	public AtomCategory createCategory() {
		Document d = XMLParser.parse(TEMPLATE_CATEGORY);
		return new AtomCategoryImpl(d.getDocumentElement());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createCategory(com.google.gwt.xml.client.Element)
	 */
	public AtomCategory createCategory(Element categoryElement) {

		// guard conditions
		
		if (!categoryElement.getTagName().equals(Atom.ELEMENT_CATEGORY)) {
			throw new AtomFormatException("bad tag name, expected: "+Atom.ELEMENT_CATEGORY+"; found: "+categoryElement.getTagName());
		}

		if (categoryElement.getNamespaceURI() == null || !categoryElement.getNamespaceURI().equals(Atom.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Atom.NSURI+"; found: "+categoryElement.getNamespaceURI());
		}

		return new AtomCategoryImpl(categoryElement);

	}

}
