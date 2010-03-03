/**
 * 
 */
package org.cggh.chassis.generic.atom.client.vanilla;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class VanillaAtomEntryImpl 
	extends AtomEntryImpl 
	implements VanillaAtomEntry {

	/**
	 * @param e
	 * @param factory
	 */
	protected VanillaAtomEntryImpl(
			Element e, 
			VanillaAtomFactory factory) {
		super(e, factory);
	}

}
