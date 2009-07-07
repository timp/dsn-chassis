/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import java.util.List;

import org.cggh.chassis.gwt.lib.atom.client.AtomNS;
import org.cggh.chassis.gwt.lib.xml.client.XML;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyFeed {

	private Document doc = null;
	private String template = 
		"<feed xmlns=\"http://www.w3.org/2005/AtomNS\"><title></title></feed>";
	
	public StudyFeed() {
		this.doc = XMLParser.parse(template);
	}
	
	protected StudyFeed(Document doc) {
		// TODO check doc form here?
		this.doc = doc;
	}
	
	public static StudyFeed parse(String xml) {
		Document parsed = XMLParser.parse(xml);
		// TODO check document form?
		return new StudyFeed(parsed);
	}
	
	public void setTitle(String title) {
		Element titleElement = XML.getElementByTagNameNS(doc, AtomNS.NS, AtomNS.TITLE);
		if (titleElement != null) {
			XML.removeAllChildren(titleElement);
			titleElement.appendChild(doc.createTextNode(title));
		}
		else {
			// TODO anything?
		}
	}
	
	public String getId() {
		return XML.getSimpleContentByTagNameNS(this.doc, AtomNS.NS, AtomNS.ID);
	}
	
	public String getUpdated() {
		return XML.getSimpleContentByTagNameNS(this.doc, AtomNS.NS, AtomNS.UPDATED);		
	}
	
	public String getTitle() {
		return XML.getSimpleContentByTagNameNS(this.doc, AtomNS.NS, AtomNS.TITLE);
	}
	
	@Override
	public String toString() {
		return this.doc.toString();
	}
	
}
