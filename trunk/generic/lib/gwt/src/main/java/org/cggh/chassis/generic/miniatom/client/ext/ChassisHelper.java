package org.cggh.chassis.generic.miniatom.client.ext;

import java.util.List;

import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * A helper to extract fields from a Chassis document.
 * 
 * @author aliman
 */
public class ChassisHelper extends AtomHelper {

	
	
	
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
	
	private static String getSubmissionPublished(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Chassis.ELEMENT_SUBMISSIONPUBLISHED, Chassis.NSURI);
	}	
	public static String getSubmissionPublishedAsDate(Element parent) {
		return timestampAsDate(getSubmissionPublished(parent));
	}	

	private static String getReviewPublished(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Chassis.ELEMENT_REVIEWPUBLISHED, Chassis.NSURI);
	}	
	public static String getReviewPublishedAsDate(Element parent) {
		return timestampAsDate(getReviewPublished(parent));
	}	
	
	public static String getReviewSummary(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Chassis.ELEMENT_REVIEWSUMMARY, Chassis.NSURI);
	}		
}
