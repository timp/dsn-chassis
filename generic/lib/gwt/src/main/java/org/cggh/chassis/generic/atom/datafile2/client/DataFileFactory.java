/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile2.client;

import org.cggh.chassis.generic.atom.datafile2.client.DataFileEntry;
import org.cggh.chassis.generic.atom.datafile2.client.DataFileFeed;
import org.cggh.chassis.generic.atom.rewrite.client.Atom;
import org.cggh.chassis.generic.atom.rewrite.client.AtomFactory;

import com.google.gwt.xml.client.Element;


/**
 * @author aliman
 *
 */
public class DataFileFactory extends AtomFactory<DataFileEntry, DataFileFeed> {

	
	
	
	
	public static String TEMPLATE_DATAFILEENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\"http://www.cggh.org/2009/chassis/type/\" term=\"DataFile\"/>" +
		"</atom:entry>";
	

	
	public static String TEMPLATE_DATAFILEFEED = 
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
		return TEMPLATE_DATAFILEENTRY;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.rewrite.AtomFactory#getFeedTemplate()
	 */
	@Override
	public String getFeedTemplate() {
		return TEMPLATE_DATAFILEFEED;
	}



}
