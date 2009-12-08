/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.review;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

public class ReviewEntryImpl extends AtomEntryImpl implements ReviewEntry {

	private ReviewFactory reviewFactory;


	protected ReviewEntryImpl(Element e, ReviewFactory factory) {
		super(e, factory);
		this.reviewFactory = factory;
	}




	private Element getReviewElement() {
		return XMLNS.getFirstElementByTagNameNS(element, Chassis.Element.REVIEW, Chassis.NSURI);

	}

	
	public Review getReview() {
		return reviewFactory.createReview(getReviewElement());
	}




	public void setSubmissionLink(String submissionEntryUrl) {
		// TODO consider refactor to super-class method like removeLinks(String rel)
		
		for (AtomLink link : this.getLinks()) {
			if (Chassis.Rel.REVIEW.equals(link.getRel())) {
				this.removeLink(link);
			}
		}

		this.addLink(submissionEntryUrl, Chassis.Rel.REVIEW);
		
	}
}
