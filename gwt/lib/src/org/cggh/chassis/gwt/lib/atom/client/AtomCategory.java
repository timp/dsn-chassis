/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomCategory {

	String term, scheme, label;

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
	AtomCategory(Element categoryElement) {
		// TODO Auto-generated constructor stub
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

//	/**
//	 * TODO document me
//	 * 
//	 * @param doc
//	 * @param categoryElement
//	 */
//	void populate(Document doc, Element categoryElement) {
//		// TODO Auto-generated method stub
//		
//	}

	public String toXML() {
		String xml = 
			"<category>";
		
		// output term
		if (this.term != null) {
			xml += 
				"<term>"+this.term+"</term>";
		}

		// output scheme
		if (this.scheme != null) {
			xml += 
				"<scheme>"+this.scheme+"</scheme>";
		}

		// output label
		if (this.label != null) {
			xml += 
				"<label>"+this.label+"</label>";
		}

		xml +=
			"</category>";
		
		return xml;
	}
	
}
