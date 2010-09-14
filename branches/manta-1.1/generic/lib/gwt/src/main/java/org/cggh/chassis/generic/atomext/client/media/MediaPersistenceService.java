/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.media;

import org.cggh.chassis.generic.atom.client.AtomServiceImpl;

/**
 * @author aliman
 *
 */
public class MediaPersistenceService extends
		AtomServiceImpl<MediaEntry, MediaFeed> {
	
	/**
	 * @param factory
	 */
	public MediaPersistenceService() {
		super(new MediaFactory());
	}

	/**
	 * @param factory
	 */
	public MediaPersistenceService(
			MediaFactory factory) {
		super(factory);
	}

}
