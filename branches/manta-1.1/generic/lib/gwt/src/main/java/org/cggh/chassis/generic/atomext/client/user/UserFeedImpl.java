/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.user;

import org.cggh.chassis.generic.atom.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class UserFeedImpl 
	extends AtomFeedImpl<UserEntry, UserFeed> 
	implements UserFeed {

	/**
	 * @param feedElement
	 * @param factory
	 */
	protected UserFeedImpl(Element feedElement, UserFactory factory) {
		super(feedElement, factory);
	}

}
