/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.xml.client.XML;
import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyLocation {

	private String lon;
	private String lat;

	public StudyLocation(String lat, String lon) {
		this.setLat(lat); this.setLon(lon);
	}

	/**
	 * @param locationElement
	 */
	StudyLocation(Element locationElement) {
		this.lat= XML.getSimpleContentByTagNameNS(locationElement, ChassisNS.NS, ChassisNS.LAT);
		this.lon = XML.getSimpleContentByTagNameNS(locationElement, ChassisNS.NS, ChassisNS.LON);
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

	/**
	 * @return the lon
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * TODO document me
	 * 
	 * @param doc
	 * @param studyLocationElement
	 */
	void populate(Element studyLocationElement) {
		
		Element latElement = XML.createElement(studyLocationElement, ChassisNS.LAT);
		XML.setSimpleContent(latElement, this.lat);

		Element lonElement = XML.createElement(studyLocationElement, ChassisNS.LON);
		XML.setSimpleContent(lonElement, this.lon);

	}
	
}
