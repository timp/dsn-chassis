/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.user;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class UserEntryImpl extends AtomEntryImpl implements UserEntry {

	/**
	 * @param e
	 * @param factory
	 */
	protected UserEntryImpl(Element e, UserFactory factory) {
		super(e, factory);
	}

}
