/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetLinkImpl;
import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class StudyEntryImpl 
	extends AtomEntryImpl 
	implements StudyEntry {
	
	
	
	
	private StudyFactory studyFactory;

	
	
	/**
	 * @param e
	 * @param factory
	 */
	protected StudyEntryImpl(Element e, StudyFactory factory) {
		super(e, factory);
		this.studyFactory = factory;
	}


	
	public Element getStudyElement() {
		return XMLNS.getFirstElementByTagNameNS(element, Chassis.Element.STUDY, Chassis.NSURI);
	}



	
	
	public Study getStudy() {
		return studyFactory.createStudy(getStudyElement());
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.study.StudyEntry#getDatasetLinks()
	 */
	public List<DatasetLink> getDatasetLinks() {
		List<AtomLink> links = this.getLinks();
		List<DatasetLink> datasetLinks = new ArrayList<DatasetLink>();
		for (AtomLink link : links) {
			if (link.getRel() != null && link.getRel().equals(Chassis.Rel.DATASET)) {
				datasetLinks.add(new DatasetLinkImpl(link.getElement()));
			}
		}
		
		return datasetLinks;
	}

	
	



}
