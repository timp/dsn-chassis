/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.review;

import org.cggh.chassis.generic.atom.client.Atom;
import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atomext.shared.Chassis;

import com.google.gwt.xml.client.Element;

public class ReviewFactory extends AtomFactory<ReviewEntry, ReviewFeed> {

	
	public static String TEMPLATE_ENTRY = 
		"<atom:entry xmlns:atom=\""+Atom.NSURI+"\">" +
			"<atom:category scheme=\""+Chassis.SCHEME_TYPES+"\" term=\""+Chassis.Type.REVIEW+"\"/>" +
		"</atom:entry>";
	
	
	
	
	public static String TEMPLATE_FEED = 
		"<atom:feed xmlns:atom=\""+Atom.NSURI+"\"/>";
	

	
	
	
	@Override
	public ReviewEntry createEntry(Element entryElement) {
		return new ReviewEntryImpl(entryElement, this);
	}

	@Override
	public ReviewFeed createFeed(Element feedElement) {
		return new ReviewFeedImpl(feedElement, this);
	}

	@Override
	public String getEntryTemplate() {
		return TEMPLATE_ENTRY;
	}

	@Override
	public String getFeedTemplate() {
		return TEMPLATE_FEED;
	}

	public Review createReview(Element reviewElement) {
		return new ReviewImpl(reviewElement);
	}


}
