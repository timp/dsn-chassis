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
public class AtomCategory {

	private String term, scheme, label;

	/**
	 * @param term
	 * @param scheme
	 * @param label
	 */
	public AtomCategory(String term, String scheme, String label) {
		super();
		this.term = term;
		this.scheme = scheme;
		this.label = label;
	}

	/**
	 * @param categoryElement
	 */
	protected AtomCategory(Element categoryElement) {
		this.term = categoryElement.getAttribute(AtomNS.TERM);
		this.scheme = categoryElement.getAttribute(AtomNS.SCHEME);
		this.label = categoryElement.getAttribute(AtomNS.LABEL);
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return this.term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return this.scheme;
	}

	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * TODO document me
	 * 
	 * @param doc
	 * @param categoryElement
	 */
	void populate(Element categoryElement) {

		// term element (mandatory)
		categoryElement.setAttribute(AtomNS.TERM, this.term);
		
		// scheme element (optional)
		if (this.scheme != null) {
			categoryElement.setAttribute(AtomNS.SCHEME, this.scheme);
		}

		// label element (optional)
		if (this.label != null) {
			categoryElement.setAttribute(AtomNS.LABEL, this.label);
		}
		
	}

	/**
	 * TODO document me
	 * 
	 * @param categoryElements
	 * @return
	 */
	public static List<AtomCategory> getCategories(Element parent) {
		List<Element> categoryElements = XML.getElementsByTagName(parent, AtomNS.CATEGORY);
		List<AtomCategory> categories = new ArrayList<AtomCategory>();
		for (Element categoryElement : categoryElements) {
			categories.add(new AtomCategory(categoryElement));
		}
		return categories;
	}

	/**
	 * TODO document me
	 * 
	 * @param entryElement
	 */
	public static void setCategories(Element parent, List<AtomCategory> categories) {

		// remove existing category elements
		XML.removeElementsByTagName(parent, AtomNS.CATEGORY);
		
		// create new category elements and append to entry element
		for (AtomCategory category : categories) {
			Element categoryElement = XML.createElement(parent, AtomNS.CATEGORY);
			category.populate(categoryElement);
		}
		
	}
	
}
