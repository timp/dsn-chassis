/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.review;

import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atom.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;

public class ReviewFeedImpl 
    extends AtomFeedImpl<ReviewEntry, ReviewFeed> 
    implements ReviewFeed {

	
	
	protected ReviewFeedImpl(Element feedElement, AtomFactory<ReviewEntry, ReviewFeed> factory) {
		super(feedElement, factory);
	}

	
	
}
