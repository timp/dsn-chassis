package org.cggh.chassis.generic.atomext.client.review;

import org.cggh.chassis.generic.atom.client.AtomServiceImpl;

/**
 * @author timp
 * @since 04/12/2009
 */
public class ReviewPersistenceService 
    extends AtomServiceImpl<ReviewEntry, ReviewFeed> {

	
	public ReviewPersistenceService(ReviewFactory factory) {
		super(factory);
	}

	public ReviewPersistenceService() {
		super(new ReviewFactory());
	}

	public ReviewPersistenceService(String baseUrl) {
		super(new ReviewFactory(), baseUrl);
	}

	
}
