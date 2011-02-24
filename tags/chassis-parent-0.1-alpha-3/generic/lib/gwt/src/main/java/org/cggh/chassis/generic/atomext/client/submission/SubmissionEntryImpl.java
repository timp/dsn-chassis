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
import org.cggh.chassis.generic.atomext.shared.Chassis;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmissionEntryImpl 
	extends AtomEntryImpl 
	implements SubmissionEntry {
	
	
	
	
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

		

}