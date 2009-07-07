/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

import org.cggh.chassis.gwt.lib.xml.client.XML;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomPersonConstruct {

	String uri;
	String email;
	String name;

	/**
	 * @return the uri
	 */
	public String getUri() {
		return this.uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public AtomPersonConstruct(String name, String email, String uri) {
		this.name = name;
		this.email = email;
		this.uri = uri;
	}

	/**
	 * @param authorElement
	 */
	AtomPersonConstruct(Element authorElement) {
		this.name = XML.getSimpleContentByTagNameNS(authorElement, AtomNS.NS, AtomNS.NAME);
		this.email = XML.getSimpleContentByTagNameNS(authorElement, AtomNS.NS, AtomNS.EMAIL);
		this.uri = XML.getSimpleContentByTagNameNS(authorElement, AtomNS.NS, AtomNS.URI);
	}

	/**
	 * TODO document me
	 * 
	 * @param authorElement
	 */
	void populate(Document doc, Element authorElement) {

		// name element (mandatory)
		Element nameElement = doc.createElement(AtomNS.NAME);
		authorElement.appendChild(nameElement);
		nameElement.appendChild(doc.createTextNode(this.name));
		
		// email element (optional)
		if (this.email != null) {
			Element emailElement = doc.createElement(AtomNS.EMAIL);
			authorElement.appendChild(emailElement);
			emailElement.appendChild(doc.createTextNode(this.email));
		}

		// uri element (optional)
		if (this.uri != null) {
			Element uriElement = doc.createElement(AtomNS.URI);
			authorElement.appendChild(uriElement);
			uriElement.appendChild(doc.createTextNode(this.uri));
		}
}
	
	
}
