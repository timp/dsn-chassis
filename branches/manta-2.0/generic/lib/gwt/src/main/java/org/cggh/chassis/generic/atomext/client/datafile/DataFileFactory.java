/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLinkImpl;
import org.cggh.chassis.generic.atomext.shared.Chassis;

import com.google.gwt.xml.client.Element;


/**
 * @author aliman
 *
 */
public class DataFileFactory extends AtomFactory<DataFileEntry, DataFileFeed> {

	
	
	
	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\""+Chassis.SCHEME_TYPES+"\" term=\""+Chassis.Type.DATAFILE+"\"/>" +
		"</atom:entry>";
	

	
	public static String TEMPLATE_FEED = 
		"<atom:feed xmlns:atom=\""+Atom.NSURI+"\"/>";

	

	
	@Override
	public DataFileEntry createEntry(Element entryElement) {
		return new DataFileEntryImpl(entryElement, this);
	}

	
	
	
	
	@Override
	public DataFileFeed createFeed(Element feedElement) {
		return new DataFileFeedImpl(feedElement, this);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.rewrite.AtomFactory#getEntryTemplate()
	 */
	@Override
	public String getEntryTemplate() {
		return TEMPLATE_ENTRY;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.rewrite.AtomFactory#getFeedTemplate()
	 */
	@Override
	public String getFeedTemplate() {
		return TEMPLATE_FEED;
	}





	/**
	 * @param link
	 * @return
	 */
	public DatasetLink createDatasetLink(AtomLink link) {
		return new DatasetLinkImpl(link.getElement());
	}



}
