/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atom.rewrite.client.AtomEntryImpl;
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

	
	



}
