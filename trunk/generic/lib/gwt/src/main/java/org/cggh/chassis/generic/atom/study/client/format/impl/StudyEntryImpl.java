/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format.impl;

import java.util.List;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.study.client.format.Study;
import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntryImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class StudyEntryImpl extends AtomEntryImpl implements StudyEntry {

	private StudyFactory studyFactory;

	/**
	 * @param e
	 * @param factory
	 */
	protected StudyEntryImpl(Element e, StudyFactory factory) {
		super(e, factory);
		this.studyFactory = factory;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#addModule(java.lang.String)
	 */
	public void addModule(String module) {
		// TODO refactor other code to make this unecessary
		getStudy().addModule(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#getModules()
	 */
	public List<String> getModules() {
		// TODO refactor other code to make this unecessary
		return getStudy().getModules();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#removeModule(java.lang.String)
	 */
	public void removeModule(String module) {
		// TODO refactor other code to make this unecessary
		getStudy().removeModule(module);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#setModules(java.util.List)
	 */
	public void setModules(List<String> modules) {
		// TODO refactor other code to make this unecessary
		getStudy().setModules(modules);
	}
	
	public Element getStudyElement() {
//		return XML.getElementByTagNameNS(element, Chassis.NSURI, Chassis.ELEMENT_STUDY);
		return XMLNS.getFirstElementByTagNameNS(element, Chassis.Element.STUDY, Chassis.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyEntry#getStudy()
	 */
	public Study getStudy() {
		return studyFactory.createStudy(getStudyElement());
	}

}
