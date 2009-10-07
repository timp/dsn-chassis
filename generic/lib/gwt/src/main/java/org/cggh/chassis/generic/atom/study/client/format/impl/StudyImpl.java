/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format.impl;

import java.util.List;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.study.client.format.Study;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.impl.ElementWrapperImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class StudyImpl extends ElementWrapperImpl implements Study {

	private StudyFactory factory;

	/**
	 * @param e
	 * @param studyFactory 
	 */
	protected StudyImpl(Element e, StudyFactory studyFactory) {
		super(e);
		this.factory = studyFactory;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.Study#addModule(java.lang.String)
	 */
	public void addModule(String module) {
		List<String> modules = getModules();
		modules.add(module);
		setModules(modules);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.Study#getModules()
	 */
	public List<String> getModules() {
//		return XML.getElementsSimpleContentsByTagName(element, Chassis.ELEMENT_MODULE);
		return XMLNS.getElementsSimpleContentsByTagNameNS(element, Chassis.ELEMENT_MODULE, Chassis.NSURI);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.Study#removeModule(java.lang.String)
	 */
	public void removeModule(String module) {
		List<String> modules = getModules();
		modules.remove(module);
		setModules(modules);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.Study#setModules(java.util.List)
	 */
	public void setModules(List<String> modules) {
//		XML.setElementsSimpleContentsByLocalName(element, Chassis.ELEMENT_MODULE, modules);
		XMLNS.setElementsSimpleContentsByTagNameNS(element, Chassis.ELEMENT_MODULE, Chassis.PREFIX, Chassis.NSURI, modules);
	}

}
