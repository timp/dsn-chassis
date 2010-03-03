/**
 * 
 */
package org.cggh.chassis.generic.miniatom.client.ext;

import java.util.List;

import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class ChassisHelper {

	
	
	
	public static Element getStudy(Element parent) {
		return XMLNS.getFirstChildByTagNameNS(parent, Chassis.ELEMENT_STUDY, Chassis.NSURI);
	}
	
	
	
	
	public static Element createStudy() {
		return XMLNS.createElementNS(Chassis.ELEMENT_STUDY, Chassis.PREFIX, Chassis.NSURI);
	}
	
	
	
	
	public static List<String> getModules(Element parent) {
		return XMLNS.getElementsSimpleContentsByTagNameNS(parent, Chassis.ELEMENT_MODULE, Chassis.NSURI);
	}
	
	
	
	public static void removeModules(Element parent) {
		XMLNS.removeChildrenByTagNameNS(parent, Chassis.ELEMENT_MODULE, Chassis.NSURI);
	}
	
	
	
	public static void setModules(Element parent, List<String> modules) {
		XMLNS.setElementsSimpleContentsByTagNameNS(parent, Chassis.ELEMENT_MODULE, Chassis.PREFIX, Chassis.NSURI, modules);
	}

 
	public static void setOutcome(Element parent, String outcome) {
		XMLNS.setSingleChildSimpleContentByTagNameNS(parent, Chassis.ELEMENT_OUTCOME, Chassis.PREFIX, Chassis.NSURI, outcome);
	}


	public static Element createReview() {
		return XMLNS.createElementNS(Chassis.ELEMENT_REVIEW, Chassis.PREFIX, Chassis.NSURI);
	}

	
}
