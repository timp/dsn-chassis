/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format.impl;

import java.util.List;

import org.cggh.chassis.generic.atom.chassis.base.client.format.ChassisFormatException;
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

	public int getEndYear() throws ChassisFormatException {
		String content = XMLNS.getFirstElementSimpleContentByTagNameNS(element, Chassis.ELEMENT_ENDYEAR, Chassis.NSURI);
		int year = -1;
		try {
			year = Integer.parseInt(content);
		}
		catch (NumberFormatException e) {
			throw new ChassisFormatException("could not parse value "+content+" as integer");
		}
		// TODO check range of year???
		return year;
	}

	public int getStartYear() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setEndYear(int endYear) {
		String content = Integer.toString(endYear);
		XMLNS.setSingleElementSimpleContentByTagNameNS(element, Chassis.ELEMENT_ENDYEAR, Chassis.PREFIX, Chassis.NSURI, content);
	}

	public void setStartYear(int startYear) {
		// TODO Auto-generated method stub
		
	}

}
