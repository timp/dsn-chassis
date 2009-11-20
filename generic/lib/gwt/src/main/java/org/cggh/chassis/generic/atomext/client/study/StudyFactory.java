/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLinkImpl;
import org.cggh.chassis.generic.atomext.shared.Chassis;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class StudyFactory extends AtomFactory<StudyEntry, StudyFeed> {
	
	
	
	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\""+Chassis.SCHEME_TYPES+"\" term=\""+Chassis.Type.STUDY+"\"/>" +
			"<atom:content type=\"application/xml\">" +
				"<chassis:study xmlns:chassis=\""+Chassis.NSURI+"\"></chassis:study>" +
			"</atom:content>" +
		"</atom:entry>";
	
	
	
	
	public static String TEMPLATE_FEED = 
		"<atom:feed xmlns:atom=\""+Atom.NSURI+"\"/>";

	
	
	
	

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#createEntry(com.google.gwt.xml.client.Element)
	 */
	@Override
	public StudyEntry createEntry(Element e) {
		return new StudyEntryImpl(e, this);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#createFeed(com.google.gwt.xml.client.Element)
	 */
	@Override
	public StudyFeed createFeed(Element e) {
		return new StudyFeedImpl(e, this);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#getEntryTemplate()
	 */
	@Override
	public String getEntryTemplate() {
		return TEMPLATE_ENTRY;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#getFeedTemplate()
	 */
	@Override
	public String getFeedTemplate() {
		return TEMPLATE_FEED;
	}





	/**
	 * @param studyElement
	 * @return
	 */
	public Study createStudy(Element studyElement) {
		return new StudyImpl(studyElement);
	}

	
	
	
	public DatasetLink createDatasetLink(AtomLink link) {
		return new DatasetLinkImpl(link.getElement());
	}
	
	
	
	
}
