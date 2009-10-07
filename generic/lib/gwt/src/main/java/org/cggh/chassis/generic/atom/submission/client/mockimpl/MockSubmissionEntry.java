/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomEntry;

/**
 * @author aliman
 *
 */
public class MockSubmissionEntry extends MockAtomEntry implements SubmissionEntry {

	private List<String> modules = new ArrayList<String>();

	/**
	 * @param mockAtomFactory
	 */
	protected MockSubmissionEntry(MockSubmissionFactory factory) {
		super(factory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#addModule(java.lang.String)
	 */
	public void addModule(String module) {
		this.modules.add(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#getModules()
	 */
	public List<String> getModules() {
		return new ArrayList<String>(this.modules);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#removeModule(java.lang.String)
	 */
	public void removeModule(String module) {
		this.modules.remove(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#setModules(java.util.List)
	 */
	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#addStudyLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void addStudyLink(String href) {

		// make sure we don't add duplicates
		for (AtomLink link : this.getStudyLinks()) {
			if (link.getHref().equals(href)) {
				return;
			}
		}
		
		// create and add a new link
		AtomLink link = factory.createLink();
		link.setRel(Chassis.REL_STUDY);
		link.setHref(href);
		this.links.add(link);
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#getStudyLinks()
	 */
	public List<AtomLink> getStudyLinks() {
		return this.getLinksByRel(Chassis.REL_STUDY);
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#getStudyLink(java.lang.String)
	 */
	public AtomLink getStudyLink(String href) {
		for (AtomLink link : this.getStudyLinks()) {
			if (link.getHref().equals(href)) {
				return link;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#removeStudyLink(org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink)
	 */
	public void removeStudyLink(String href) {
		for (AtomLink link : this.getStudyLinks()) {
			if (link.getHref().equals(href)) {
				this.removeLink(link);
			}
		}
	}
	
	public void removeAllStudyLinks() {
		for (AtomLink link : this.getStudyLinks()) {
			this.removeLink(link);
		}
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry#setStudyLinks(java.util.List)
	 */
	public void setStudyLinks(List<String> hrefs) {
		this.removeAllStudyLinks();
		for (String href : hrefs) {
			this.addStudyLink(href);
		}
	}

	@Override
	public void put(AtomEntry entry) {
		super.put(entry);
		if (entry instanceof SubmissionEntry) {
			SubmissionEntry study = (SubmissionEntry) entry;
			this.modules = new ArrayList<String>(study.getModules());
		}
	}

}
