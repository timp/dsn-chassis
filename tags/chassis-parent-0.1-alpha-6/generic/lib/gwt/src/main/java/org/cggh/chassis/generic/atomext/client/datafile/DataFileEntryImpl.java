/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;
import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DataFileEntryImpl extends AtomEntryImpl implements DataFileEntry {
	
	
	
	
	private Log log = LogFactory.getLog(DataFileEntryImpl.class);
	private DataFileFactory dataFileFactory;

	
	
	
	/**
	 * @param e
	 * @param factory
	 */
	public DataFileEntryImpl(Element e, DataFileFactory factory) {
		super(e, factory);
		this.dataFileFactory = factory;
	}

	
	

	public List<RevisionLink> getRevisionLinks() {
		log.enter("getFileRevisionLinks");
		
		List<RevisionLink> links = new ArrayList<RevisionLink>();
		for (AtomLink l : this.getLinks()) {
			if (l.getRel().equals(Chassis.Rel.REVISION)) {
				links.add(RevisionLinkImpl.as(l));
			}
		}
		
		log.leave();
		return links;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.study.StudyEntry#getDatasetLinks()
	 */
	public List<DatasetLink> getDatasetLinks() {
		List<AtomLink> links = this.getLinks();
		List<DatasetLink> datasetLinks = new ArrayList<DatasetLink>();
		for (AtomLink link : links) {
			if (link.getRel() != null && link.getRel().equals(Chassis.Rel.DATASET)) {
				datasetLinks.add(this.dataFileFactory.createDatasetLink(link));
			}
		}
		
		return datasetLinks;
	}

	
	



	

}
