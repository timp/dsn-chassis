/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileLink;
import org.cggh.chassis.generic.atomext.client.study.StudyLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionLinkImpl;
import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DatasetEntryImpl extends AtomEntryImpl implements DatasetEntry {


	
	
	
	private DatasetFactory datasetFactory;
	private Log log = LogFactory.getLog(DatasetEntryImpl.class);

	
	
	
	
	/**
	 * @param e
	 * @param factory
	 */
	protected DatasetEntryImpl(Element e, DatasetFactory factory) {
		super(e, factory);
		this.datasetFactory = factory;
	}


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#getDataFileLinks()
	 */
	public List<DataFileLink> getDataFileLinks() {
		log.enter("getDataFileLinks");
		
		List<AtomLink> links = this.getLinks();
		List<DataFileLink> dataFileLinks = new ArrayList<DataFileLink>();
		for (AtomLink link : links) {
			if (link.getRel() != null && link.getRel().equals(Chassis.Rel.DATAFILE)) {
				dataFileLinks.add(this.datasetFactory.createDataFileLink(link));
			}
		}
		
		log.leave();
		return dataFileLinks;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#getStudyLinks()
	 */
	public List<StudyLink> getStudyLinks() {
		log.enter("getStudyLinks");
		
		List<AtomLink> links = this.getLinks();
		List<StudyLink> studyLinks = new ArrayList<StudyLink>();
		for (AtomLink link : links) {
			if (link.getRel() != null && link.getRel().equals(Chassis.Rel.STUDY)) {
				studyLinks.add(this.datasetFactory.createStudyLink(link));
			}
		}
		
		log.leave();
		return studyLinks;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#addStudyLink(java.lang.String)
	 */
	public void addStudyLink(String href) {
		super.addLink(href, Chassis.Rel.STUDY);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#addStudyLink(int, java.lang.String)
	 */
	public void addStudyLink(int index, String href) {
		log.enter("addStudyLink");
		List<StudyLink> studyLinks = this.getStudyLinks();

		if (studyLinks.size() == 0) {
			log.warn("no existing study links, adding anywhere");
			this.addStudyLink(href);
		}
		else if (index == 0) {
			StudyLink first = studyLinks.get(0);
			super.addLinkBefore(first.getElement(), href, Chassis.Rel.STUDY);
		}
		else {

			if (index > studyLinks.size()) {
				index = studyLinks.size();
				log.warn("index was greater than number of study links, resetting to maximum legal value");
			}
			
			StudyLink before = studyLinks.get(index-1);
			super.addLinkAfter(before.getElement(), href, Chassis.Rel.STUDY);

		}
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#removeStudyLink(java.lang.String)
	 */
	public void removeStudyLink(String href) {
		List<StudyLink> links = this.getStudyLinks();
		for (StudyLink link : links) {
			if (link.getHref().equals(href)) {
				this.removeLink(link);
			}
		}
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#removeStudyLink(int)
	 */
	public void removeStudyLink(int index) {
		List<StudyLink> links = this.getStudyLinks();
		if (index >= links.size()) {
			throw new IndexOutOfBoundsException("maximum legal value is "+(links.size()-1));
		}
		StudyLink link = links.get(index);
		this.removeLink(link);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#addDataFileLink(java.lang.String)
	 */
	public void addDataFileLink(String href) {
		super.addLink(href, Chassis.Rel.DATAFILE);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#addDataFileLink(int, java.lang.String)
	 */
	public void addDataFileLink(int index, String href) {
		log.enter("addDataFileLink");
		List<DataFileLink> dataFileLinks = this.getDataFileLinks();

		if (dataFileLinks.size() == 0) {
			log.warn("no existing data file links, adding anywhere");
			this.addDataFileLink(href);
		}
		else if (index == 0) {
			DataFileLink first = dataFileLinks.get(0);
			super.addLinkBefore(first.getElement(), href, Chassis.Rel.DATAFILE);
		}
		else {

			if (index > dataFileLinks.size()) {
				index = dataFileLinks.size();
				log.warn("index was greater than number of study links, resetting to maximum legal value");
			}
			
			DataFileLink before = dataFileLinks.get(index-1);
			super.addLinkAfter(before.getElement(), href, Chassis.Rel.DATAFILE);

		}
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#removeDataFileLink(java.lang.String)
	 */
	public void removeDataFileLink(String href) {
		List<DataFileLink> links = this.getDataFileLinks();
		for (DataFileLink link : links) {
			if (link.getHref().equals(href)) {
				this.removeLink(link);
			}
		}
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#removeDataFileLink(int)
	 */
	public void removeDataFileLink(int index) {
		List<DataFileLink> links = this.getDataFileLinks();
		if (index >= links.size()) {
			throw new IndexOutOfBoundsException("maximum legal value is "+(links.size()-1));
		}
		DataFileLink link = links.get(index);
		this.removeLink(link);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#getSubmissionLink()
	 */
	public SubmissionLink getSubmissionLink() {
		
		List<AtomLink> links = this.getLinks();
		SubmissionLink submissionLink = null;
		
		for (AtomLink link : links) {
			if (Chassis.Rel.SUBMISSION.equals(link.getRel())) {
				submissionLink = new SubmissionLinkImpl(link.getElement());
			}
		}
		
		// TODO should we throw an exception if we find more than one submission link?
		
		return submissionLink;
	}
	
	
	

}
