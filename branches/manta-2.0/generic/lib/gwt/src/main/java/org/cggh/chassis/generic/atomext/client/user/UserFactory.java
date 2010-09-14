/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.user;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atomext.shared.Chassis;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class UserFactory extends AtomFactory<UserEntry, UserFeed> {

	
	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\""+Chassis.SCHEME_TYPES+"\" term=\""+Chassis.Type.USER+"\"/>" +
		"</atom:entry>";
	
	
	
	
	public static String TEMPLATE_FEED = 
		"<atom:feed xmlns:atom=\""+Atom.NSURI+"\"/>";

	
	
	

	
	
	@Override
	public UserEntry createEntry(Element entryElement) {
		return new UserEntryImpl(entryElement, this);
	}

	
	
	@Override
	public UserFeed createFeed(Element feedElement) {
		return new UserFeedImpl(feedElement, this);
	}

	
	
	
	@Override
	public String getEntryTemplate() {
		return TEMPLATE_ENTRY;
	}


	
	
	@Override
	public String getFeedTemplate() {
		return TEMPLATE_FEED;
	}

}
