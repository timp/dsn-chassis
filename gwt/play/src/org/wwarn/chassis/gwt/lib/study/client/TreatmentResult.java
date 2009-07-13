/**
 * $Id$
 */
package org.wwarn.chassis.gwt.lib.study.client;

import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.xml.client.XML;
import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TreatmentResult {

	
	
	private static final String TREATMENT = "treatment";
	private static final String RISKADJUSTED = "riskadjusted";
	private static final String RISKUNADJUSTED = "riskunadjusted";
	private static final String DAY = "day";
	
	
	
	String treatment, day, riskAdjusted, riskUnadjusted;
	
	
	
	/**
	 * @param treatmentResultElement
	 */
	TreatmentResult(Element treatmentResultElement) {
		this.treatment = XML.getElementSimpleContentByTagNameNS(treatmentResultElement, ChassisNS.NS, TREATMENT);
		this.day = XML.getElementSimpleContentByTagNameNS(treatmentResultElement, ChassisNS.NS, DAY);
		this.riskAdjusted = XML.getElementSimpleContentByTagNameNS(treatmentResultElement, ChassisNS.NS, RISKADJUSTED);
		this.riskUnadjusted = XML.getElementSimpleContentByTagNameNS(treatmentResultElement, ChassisNS.NS, RISKUNADJUSTED);
	}

	
	
	/**
	 * TODO document me
	 * 
	 * @param doc
	 * @param treatmentElement
	 */
	void populate(Element treatmentResultElement) {

		// create and populate treatment element
		Element treatmentElement = XML.createElement(treatmentResultElement, TREATMENT);
		XML.setSimpleContent(treatmentElement, this.treatment);

		// create and populate day element
		Element dayElement = XML.createElement(treatmentResultElement, DAY);
		XML.setSimpleContent(dayElement, this.day);
		
		// create and populate risk adjusted element
		Element riskAdjustedElement = XML.createElement(treatmentResultElement, RISKADJUSTED);
		XML.setSimpleContent(riskAdjustedElement, this.riskAdjusted);

		// create and populate risk unadjusted element
		Element riskUnadjustedElement = XML.createElement(treatmentResultElement, RISKUNADJUSTED);
		XML.setSimpleContent(riskUnadjustedElement, this.riskUnadjusted);

	}

	
	
	
	/**
	 * @return the treatment
	 */
	public String getTreatment() {
		return this.treatment;
	}

	/**
	 * @param treatment the treatment to set
	 */
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return this.day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the riskAdjusted
	 */
	public String getRiskAdjusted() {
		return this.riskAdjusted;
	}

	/**
	 * @param riskAdjusted the riskAdjusted to set
	 */
	public void setRiskAdjusted(String riskAdjusted) {
		this.riskAdjusted = riskAdjusted;
	}

	/**
	 * @return the riskUnadjusted
	 */
	public String getRiskUnadjusted() {
		return this.riskUnadjusted;
	}

	/**
	 * @param riskUnadjusted the riskUnadjusted to set
	 */
	public void setRiskUnadjusted(String riskUnadjusted) {
		this.riskUnadjusted = riskUnadjusted;
	}

}
