/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format.impl;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.submission.client.format.Submission;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
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
public class SubmissionFactoryImpl extends AtomFactoryImpl implements SubmissionFactory {

	
	
	public static String TEMPLATE_SUBMISSIONENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\"http://www.cggh.org/2009/chassis/type/\" term=\"submission\"/>" +
			"<atom:content type=\"application/xml\">" +
				"<chassis:submission xmlns:chassis=\""+Chassis.NSURI+"\"></chassis:submission>" +
			"</atom:content>" +
		"</atom:entry>";
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory#createSubmissionEntry()
	 */
	public SubmissionEntry createSubmissionEntry() {
		Document d = XMLParser.parse(TEMPLATE_SUBMISSIONENTRY);
		return new SubmissionEntryImpl(d.getDocumentElement(), this);
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry()
	 */
	@Override
	public AtomEntry createEntry() {
		return createSubmissionEntry();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createEntry(java.lang.String)
	 */
	@Override
	public AtomEntry createEntry(String entryDocument) {
		
		// TODO is this necessary?
		
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
		
		return new SubmissionEntryImpl(entryElement, this);

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory#createFeed(java.lang.String)
	 */
	@Override
	public AtomFeed createFeed(String feedDocument) {
		return createSubmissionFeed(feedDocument);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory#createSubmission(com.google.gwt.xml.client.Element)
	 */
	public Submission createSubmission(Element submissionElement) {
		
		if (submissionElement == null) {
			throw new NullPointerException("submissionElement is null, cannot create submission");
		}
		
		if (!XML.getLocalName(submissionElement).equals(Chassis.Element.SUBMISSION)) {
			throw new AtomFormatException("bad local tag name, expected: "+Chassis.Element.SUBMISSION+"; found: "+XML.getLocalName(submissionElement));
		}

		if (submissionElement.getNamespaceURI() == null || !submissionElement.getNamespaceURI().equals(Chassis.NSURI)) {
			throw new AtomFormatException("bad namespace URI, expected: "+Chassis.NSURI+"; found: "+submissionElement.getNamespaceURI());
		}
		
		return new SubmissionImpl(submissionElement, this);

	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory#createSubmissionFeed(java.lang.String)
	 */
	public SubmissionFeed createSubmissionFeed(String feedDocument) {
		
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
		
		return new SubmissionFeedImpl(d.getDocumentElement(), this);
		
	}
	
	

}
