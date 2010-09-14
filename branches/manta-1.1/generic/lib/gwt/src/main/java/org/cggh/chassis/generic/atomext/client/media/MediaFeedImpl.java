/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.media;

import org.cggh.chassis.generic.atom.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class MediaFeedImpl 
	extends AtomFeedImpl<MediaEntry, MediaFeed> 
	implements MediaFeed {

	/**
	 * @param feedElement
	 * @param factory
	 */
	protected MediaFeedImpl(
			Element feedElement,
			MediaFactory factory) {
		super(feedElement, factory);
	}

	
}
