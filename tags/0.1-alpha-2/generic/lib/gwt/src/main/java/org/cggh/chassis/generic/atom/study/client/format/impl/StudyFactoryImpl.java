/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format.impl;

import org.cggh.chassis.generic.atom.chassis.base.client.format.Chassis;
import org.cggh.chassis.generic.atom.study.client.format.Study;
import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
import org.cggh.chassis.generic.atom.vanilla.client.format.impl.AtomEntryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.impl.AtomFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.impl.AtomFeedImpl;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;


/**
 * @author aliman
 *
 */
public class StudyFactoryImpl extends AtomFactoryImpl implements StudyFactory {

	
	
	private static String TEMPLATE_STUDYENTRY = 
		"<entry xmlns=\""+Atom.NSURI+"\">" +
			"<content type=\"application/xml\">" +
				"<study xmlns=\""+Chassis.NSURI+"\"></study>" +
			"</content>\n" +
		"</entry>";
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyFactory#createStudyEntry()
	 */
	public StudyEntry createStudyEntry() {
		Document d = XMLParser.parse(TEMPLATE_STUDYENTRY);
		return new StudyEntryImpl(d.getDocumentElement(), this);
	}

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry()
	 */
	@Override
	public AtomEntry createEntry() {
		return createStudyEntry();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(java.lang.String)
	 */
	@Override
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
	@Override
	public AtomEntry createEntry(Element entryElement) {

		// guard conditions
		// TODO refactor
		
		if (!entryElement.getTagName().equals(Atom.ELEMENT_ENTRY)) {
			throw new AtomFormatException("bad tag name, expected: "+Atom.ELEMENT_ENTRY+"; found: "+entryElement.getTagName());
		}

		if (entryElement.getNamespaceURI() == null || !entryElement.getNamespaceURI().equals(Atom.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Atom.NSURI+"; found: "+entryElement.getNamespaceURI());
		}
		
		return new StudyEntryImpl(entryElement, this);

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createFeed(java.lang.String)
	 */
	@Override
	public AtomFeed createFeed(String feedDocument) {

		Document d;
		Element feedElement;
		
		// guard conditions
		// TODO refactor
		
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
		
		return new StudyFeedImpl(d.getDocumentElement(), this);

	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyFactory#createStudy(com.google.gwt.xml.client.Element)
	 */
	public Study createStudy(Element studyElement) {
		
		// TODO guard conditions
		
		return new StudyImpl(studyElement, this);

	}
	
	
	
}
