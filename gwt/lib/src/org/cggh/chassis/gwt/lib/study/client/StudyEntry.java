/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import com.google.gwt.xml.client.Document;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyEntry {

	private Document doc;
	
	private String template = 
		"TODO";
	
	/**
	 * Create a new StudyEntry entry.
	 */
	public StudyEntry() {
		// TODO 
	}
	
	/**
	 * Wrap the given XML document.
	 * @param doc
	 */
	protected StudyEntry(Document doc) {
		this.doc = doc;
	}
	
	/**
	 * Parse the XML string and return a wrapper for the XML document.
	 * @param xml
	 * @return
	 */
	public static StudyEntry parseXML(String xml) {
		// TODO
		return null;
	}
	
	public String toXMLString() {
		// TODO
		return null;
	}
	
	public void setTitle(String title) {
		// TODO
	}
	
	public void setSummary(String summary) {
		// TODO
	}

	

}
