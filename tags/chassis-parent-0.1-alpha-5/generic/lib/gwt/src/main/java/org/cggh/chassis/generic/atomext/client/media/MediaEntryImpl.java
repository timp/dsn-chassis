/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.media;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class MediaEntryImpl 
	extends AtomEntryImpl 
	implements MediaEntry {

	/**
	 * @param e
	 * @param factory
	 */
	protected MediaEntryImpl(
			Element e, 
			MediaFactory factory) {
		super(e, factory);
	}

}
