/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomService<K extends AtomEntry, V extends AtomFeed> {
	
	private String collectionURL;

	public AtomService(String collectionURL) {
		this.collectionURL = collectionURL;
	}

}
