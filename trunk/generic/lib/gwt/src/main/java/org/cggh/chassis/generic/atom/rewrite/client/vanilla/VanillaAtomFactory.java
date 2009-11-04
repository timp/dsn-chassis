/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client.vanilla;

import org.cggh.chassis.generic.atom.rewrite.client.Atom;
import org.cggh.chassis.generic.atom.rewrite.client.AtomFactory;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class VanillaAtomFactory extends AtomFactory<VanillaAtomEntry, VanillaAtomFeed> {


	
	
	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\"/>";
	

	
	public static String TEMPLATE_FEED = 
		"<atom:feed xmlns:atom=\""+Atom.NSURI+"\"/>";

	

	
	
	private Log log = LogFactory.getLog(VanillaAtomFactory.class);


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#createEntry(com.google.gwt.xml.client.Element)
	 */
	@Override
	public VanillaAtomEntry createEntry(Element entryElement) {
		return new VanillaAtomEntryImpl(entryElement, this);
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.rewrite.client.AtomFactory#createFeed(com.google.gwt.xml.client.Element)
	 */
	@Override
	public VanillaAtomFeed createFeed(Element feedElement) {
		return new VanillaAtomFeedImpl(feedElement, this);
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
	
	
	

}
