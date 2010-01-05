/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.review;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionLinkImpl;
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



    // TODO Move into submission specific subclass - reviews are nore general
	// TODO perhaps rename to setSubjectLink 
	public void setSubmissionLink(String submissionEntryUrl) {
		// TODO consider refactor to super-class method like removeLinks(String rel)
		
		for (AtomLink link : this.getLinks()) {
			if (Chassis.Rel.SUBMISSION.equals(link.getRel())) {
				this.removeLink(link);
			}
		}

		this.addLink(submissionEntryUrl, Chassis.Rel.SUBMISSION);
		
	}


    // TODO Move into submission specific subclass - reviews are nore general
	// TODO perhaps rename to getSubjectLink 
	public SubmissionLink getSubmissionLink() {
		AtomLink l = null;
		SubmissionLink s = null;

		// TODO Review behaviour if more than one submission possible
		for (AtomLink link : this.getLinks()) {
			if (Chassis.Rel.SUBMISSION.equals(link.getRel())) {
				l = link;
			}
		}
		if (l != null)
			s = new SubmissionLinkImpl(l.getElement());
		return s;
	}



	// TODO consider removal, not currently used
	public AtomAuthor getAuthor() {
		AtomAuthor author = null;
		for (AtomAuthor a : getAuthors() )
			if (author != null)
				throw new NotSingularException("Review (" + getEditLink().getHref() + ") has more than one Author" );
			else
				author = a;
		return author;
	}
	


}
