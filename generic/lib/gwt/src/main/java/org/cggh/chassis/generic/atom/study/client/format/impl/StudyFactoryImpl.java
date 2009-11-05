/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format.impl;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.study.client.format.Study;
import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFormatException;
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

	
	
	public static String TEMPLATE_STUDYENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\"http://www.cggh.org/2009/chassis/type/\" term=\"study\"/>" +
			"<atom:content type=\"application/xml\">" +
				"<chassis:study xmlns:chassis=\""+Chassis.NSURI+"\"></chassis:study>" +
			"</atom:content>" +
		"</atom:entry>";
	
	
	
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
		
		if (!XML.getLocalName(entryElement).equals(Atom.ELEMENT_ENTRY)) {
			throw new AtomFormatException("bad tag local name, expected: "+Atom.ELEMENT_ENTRY+"; found: "+XML.getLocalName(entryElement));
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
		return createStudyFeed(feedDocument);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyFactory#createStudyFeed(java.lang.String)
	 */
	public StudyFeed createStudyFeed(String feedDocument) {
		
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
			throw new AtomFormatException("bad tag local name, expected: "+Atom.ELEMENT_FEED+"; found: "+XML.getLocalName(feedElement));
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
		
		if (studyElement == null) {
			throw new NullPointerException("studyElement is null, cannot create study");
		}
		
		if (!XML.getLocalName(studyElement).equals(Chassis.Element.STUDY)) {
			throw new AtomFormatException("bad local tag name, expected: "+Chassis.Element.STUDY+"; found: "+XML.getLocalName(studyElement));
		}

		if (studyElement.getNamespaceURI() == null || !studyElement.getNamespaceURI().equals(Chassis.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Chassis.NSURI+"; found: "+studyElement.getNamespaceURI());
		}
		
		return new StudyImpl(studyElement, this);

	}




}
