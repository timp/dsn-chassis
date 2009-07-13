/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.format;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.xml.client.XML;

import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomLink {
	
	private Element linkElement;

	protected AtomLink(Element linkElement) {
		this.linkElement = linkElement;
	}
	
	/**
	 * @return the href
	 */
	public String getHref() {
		return linkElement.getAttribute(AtomNS.HREF);
	}

	/**
	 * @return the rel
	 */
	public String getRel() {
		return linkElement.getAttribute(AtomNS.REL);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return linkElement.getAttribute(AtomNS.TYPE);
	}

	/**
	 * @return the hreflang
	 */
	public String getHreflang() {
		return linkElement.getAttribute(AtomNS.HREFLANG);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return linkElement.getAttribute(AtomNS.TITLE);
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return linkElement.getAttribute(AtomNS.LENGTH);
	}

	/**
	 * TODO document me
	 * 
	 * @param entryElement
	 * @return
	 */
	public static List<AtomLink> getLinks(Element parent) {
		List<Element> linkElements = XML.getElementsByTagName(parent, AtomNS.LINK);
		List<AtomLink> links = new ArrayList<AtomLink>();
		for (Element linkElement : linkElements) {
			links.add(new AtomLink(linkElement));
		}
		return links;
	}

}
