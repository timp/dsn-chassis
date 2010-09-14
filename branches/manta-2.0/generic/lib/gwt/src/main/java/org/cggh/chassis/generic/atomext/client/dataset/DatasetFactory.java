/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileLink;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileLinkImpl;
import org.cggh.chassis.generic.atomext.client.study.StudyLink;
import org.cggh.chassis.generic.atomext.client.study.StudyLinkImpl;
import org.cggh.chassis.generic.atomext.shared.Chassis;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DatasetFactory extends AtomFactory<DatasetEntry, DatasetFeed> {

	
	
	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\""+Chassis.SCHEME_TYPES+"\" term=\""+Chassis.Type.DATASET+"\"/>" +
		"</atom:entry>";
	

	
	public static String TEMPLATE_FEED = 
		"<atom:feed xmlns:atom=\""+Atom.NSURI+"\"/>";

	

	

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.AtomFactory#createEntry(com.google.gwt.xml.client.Element)
	 */
	@Override
	public DatasetEntry createEntry(Element e) {
		return new DatasetEntryImpl(e, this);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.AtomFactory#createFeed(com.google.gwt.xml.client.Element)
	 */
	@Override
	public DatasetFeed createFeed(Element e) {
		return new DatasetFeedImpl(e, this);
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.AtomFactory#getEntryTemplate()
	 */
	@Override
	public String getEntryTemplate() {
		return TEMPLATE_ENTRY;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.AtomFactory#getFeedTemplate()
	 */
	@Override
	public String getFeedTemplate() {
		return TEMPLATE_FEED;
	}
	
	
	
	
	
	/**
	 * @param link
	 * @return
	 */
	public StudyLink createStudyLink(AtomLink link) {
		return new StudyLinkImpl(link.getElement());
	}





	/**
	 * @param link
	 * @return
	 */
	public DataFileLink createDataFileLink(AtomLink link) {
		return new DataFileLinkImpl(link.getElement());
	}
	
	
	
	



}
