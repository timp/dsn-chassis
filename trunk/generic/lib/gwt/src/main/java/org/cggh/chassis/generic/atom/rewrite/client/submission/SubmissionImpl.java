/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client.submission;

import java.util.List;

import org.cggh.chassis.generic.atom.chassis.base.vocab.Chassis;
import org.cggh.chassis.generic.atom.rewrite.client.ElementWrapperImpl;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmissionImpl extends ElementWrapperImpl implements Submission {



	
	/**
	 * @param e
	 */
	protected SubmissionImpl(Element e) {
		super(e);
	}


	
	
	// TODO code smell, refactor with StudyImpl
	


	
	public void addModule(String module) {
		List<String> modules = getModules();
		modules.add(module);
		setModules(modules);
	}

	
	

	public List<String> getModules() {
		return XMLNS.getElementsSimpleContentsByTagNameNS(element, Chassis.ELEMENT_MODULE, Chassis.NSURI);
	}

	
	
	
	public void removeModule(String module) {
		List<String> modules = getModules();
		modules.remove(module);
		setModules(modules);
	}

	
	
	
	public void setModules(List<String> modules) {
		XMLNS.setElementsSimpleContentsByTagNameNS(element, Chassis.ELEMENT_MODULE, Chassis.PREFIX, Chassis.NSURI, modules);
	}

	
	
	
}
