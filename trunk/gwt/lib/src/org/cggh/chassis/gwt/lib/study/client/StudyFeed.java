/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import java.util.List;

import org.cggh.chassis.gwt.lib.atom.client.Atom;
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
		"<feed xmlns=\"http://www.w3.org/2005/Atom\"><title></title></feed>";
	
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
		List<Element> elements = XML.getElementsByTagNameNS(this.doc, Atom.NS, Atom.TITLE);
		if (elements.size() > 0) {
			Element titleElement = elements.get(0);
			XML.clear(titleElement);
			titleElement.appendChild(doc.createTextNode(title));
		}
		else {
			// TODO anything?
		}
	}
	
	public String getTitle() {
		List<Element> elements = XML.getElementsByTagNameNS(this.doc, Atom.NS, Atom.TITLE);
		String title = null;
		if (elements.size() > 0) {
			title = elements.get(0).toString();
		}
		else {
			// TODO anything?
		}
		return title;
	}
	
	@Override
	public String toString() {
		return this.doc.toString();
	}
	
}
