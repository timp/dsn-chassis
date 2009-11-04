/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client.vanilla;

import org.cggh.chassis.generic.atom.rewrite.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class VanillaAtomFeedImpl 
	extends AtomFeedImpl<VanillaAtomEntry, VanillaAtomFeed> 
	implements VanillaAtomFeed {

	/**
	 * @param feedElement
	 * @param factory
	 */
	protected VanillaAtomFeedImpl(
			Element feedElement,
			VanillaAtomFactory factory) {
		super(feedElement, factory);
	}

	
}
