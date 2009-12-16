/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;


import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLinkImpl;
import org.cggh.chassis.generic.atomext.client.review.NotSingularException;
import org.cggh.chassis.generic.atomext.client.review.ReviewLink;
import org.cggh.chassis.generic.atomext.client.review.ReviewLinkImpl;
import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.log.client.Log;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmissionEntryImpl 
	extends AtomEntryImpl 
	implements SubmissionEntry {
	
	private Log log = LogFactory.getLog(SubmissionEntryImpl.class);
	
	
	
	private SubmissionFactory submissionFactory;

	
	

	/**
	 * @param e
	 * @param factory
	 */
	protected SubmissionEntryImpl(Element e, SubmissionFactory factory) {
		super(e, factory);
		this.submissionFactory = factory;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry#setDatasetLink(java.lang.String)
	 */
	public void setDatasetLink(String datasetEntryUrl) {
		
		// TODO consider refactor to super-class method like removeLinks(String rel)
		
		for (AtomLink link : this.getLinks()) {
			if (Chassis.Rel.DATASET.equals(link.getRel())) {
				this.removeLink(link);
			}
		}

		this.addLink(datasetEntryUrl, Chassis.Rel.DATASET);
		
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry#getDatasetLink()
	 */
	public DatasetLink getDatasetLink() {

		List<AtomLink> links = this.getLinks();
		
		List<DatasetLink> datasetLinks = new ArrayList<DatasetLink>();
		
		for (AtomLink link : links) {
			if (link.getRel() != null && link.getRel().equals(Chassis.Rel.DATASET)) {
				datasetLinks.add(new DatasetLinkImpl(link.getElement()));
			}
		}

		// TODO throw error if more than one
		// TODO throw error if no links found
			
		return datasetLinks.get(0);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry#getCurator()
	 */
	public Curator getCurator() {
		// TODO
		return null;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry#getReviewLinks()
	 */
	public List<ReviewLink> getReviewLinks() {
		log.enter("getReviewLinks");
		List<ReviewLink> reviewLinks = new ArrayList<ReviewLink>();
		for (AtomLink link : getLinks())
			if (link.getRel() != null && link.getRel().equals(Chassis.Rel.REVIEW)) {
				reviewLinks.add(new ReviewLinkImpl(link.getElement()));
			} else
				log.debug("Ignoring " + link.getRel());
		log.debug("Found " + reviewLinks.size() + " review links");
        log.leave();
		return reviewLinks;
	}


	
	public ReviewLink getReviewLink() {
		ReviewLink reviewLink = null;
		for (ReviewLink l : getReviewLinks() )
			if (reviewLink != null)
				throw new NotSingularException("Submission (" + getEditLink().getHref() + ") has more than one Review" );
			else
				reviewLink = l;
		return reviewLink;
	}





}
