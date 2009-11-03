/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format.impl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.submission.client.format.Submission;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmissionEntryImpl extends AtomEntryImpl implements SubmissionEntry {

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
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#addModule(java.lang.String)
	 */
	public void addModule(String module) {
		// TODO refactor other code to make this unecessary
		getSubmission().addModule(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#getModules()
	 */
	public List<String> getModules() {
		// TODO refactor other code to make this unecessary
		return getSubmission().getModules();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#removeModule(java.lang.String)
	 */
	public void removeModule(String module) {
		// TODO refactor other code to make this unecessary
		getSubmission().removeModule(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#setModules(java.util.List)
	 */
	public void setModules(List<String> modules) {
		// TODO refactor other code to make this unecessary
		getSubmission().setModules(modules);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#addStudyLink(java.lang.String)
	 */
	public void addStudyLink(String href) {
		AtomLink link = factory.createLink();
		link.setRel(Chassis.REL_STUDY);
		link.setHref(href);
		this.addLink(link);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#getStudyLink(java.lang.String)
	 */
	public AtomLink getStudyLink(String href) {
		for (AtomLink link : getStudyLinks()) {
			if (href != null && href.equals(link.getHref())) {
				return link;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#getStudyLinks()
	 */
	public List<AtomLink> getStudyLinks() {
		List<AtomLink> links = this.getLinks();
		List<AtomLink> studyLinks = new ArrayList<AtomLink>();
		for (AtomLink link : links) {
			if (Chassis.REL_STUDY.equals(link.getRel())) {
				studyLinks.add(link);
			}
		}
		return studyLinks;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#removeStudyLink(java.lang.String)
	 */
	public void removeStudyLink(String href) {
		AtomLink link = getStudyLink(href);
		this.removeLink(link);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#setStudyLinks(java.util.List)
	 */
	public void setStudyLinks(List<String> hrefs) {
		for (AtomLink link : getStudyLinks()) {
			removeLink(link);
		}
		for (String href : hrefs) {
			addStudyLink(href);
		}
	}
	
	public Element getSubmissionElement() {
//		return XML.getElementByTagNameNS(element, Chassis.NSURI, Chassis.ELEMENT_STUDY);
		return XMLNS.getFirstElementByTagNameNS(element, Chassis.ELEMENT_SUBMISSION, Chassis.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#getStudy()
	 */
	public Submission getSubmission() {
		return submissionFactory.createSubmission(getSubmissionElement());
	}



}
