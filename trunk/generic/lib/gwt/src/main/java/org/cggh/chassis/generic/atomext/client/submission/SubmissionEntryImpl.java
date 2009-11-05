/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.shared.Chassis;
import org.cggh.chassis.generic.xml.client.XMLNS;

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

	
	

	public void addStudyLink(String href) {
		AtomLink link = factory.createLink();
		link.setRel(Chassis.Rel.STUDY);
		link.setHref(href);
		this.addLink(link);
	}

	
	
	
	public AtomLink getStudyLink(String href) {
		for (AtomLink link : getStudyLinks()) {
			if (href != null && href.equals(link.getHref())) {
				return link;
			}
		}
		return null;
	}
	
	
	
	

	public List<AtomLink> getStudyLinks() {
		List<AtomLink> links = this.getLinks();
		List<AtomLink> studyLinks = new ArrayList<AtomLink>();
		for (AtomLink link : links) {
			if (Chassis.Rel.STUDY.equals(link.getRel())) {
				studyLinks.add(link);
			}
		}
		return studyLinks;
	}



	
	public void removeStudyLink(String href) {
		AtomLink link = getStudyLink(href);
		this.removeLink(link);
	}



	
	public void setStudyLinks(List<String> hrefs) {
		for (AtomLink link : getStudyLinks()) {
			removeLink(link);
		}
		for (String href : hrefs) {
			addStudyLink(href);
		}
	}
	
	
	
	
	public Element getSubmissionElement() {
		return XMLNS.getFirstElementByTagNameNS(element, Chassis.Element.SUBMISSION, Chassis.NSURI);
	}



	
	
	public Submission getSubmission() {
		return submissionFactory.createSubmission(getSubmissionElement());
	}

	
	

}
