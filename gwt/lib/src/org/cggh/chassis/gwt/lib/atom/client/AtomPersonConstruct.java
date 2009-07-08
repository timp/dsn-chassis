/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

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
	void populate(Element authorElement) {

		// name element (mandatory)
		XML.setElementSimpleContentByTagName(authorElement, AtomNS.NAME, this.name);
		
		// email element (optional)
		if (this.email != null) {
			XML.setElementSimpleContentByTagName(authorElement, AtomNS.EMAIL, this.email);
		}

		// uri element (optional)
		if (this.uri != null) {
			XML.setElementSimpleContentByTagName(authorElement, AtomNS.URI, this.uri);
		}
	}

	
	
	/**
	 * TODO document me
	 * 
	 * @param authorElements
	 * @return
	 */
	public static List<AtomPersonConstruct> getAuthors(Element parent) {
		List<Element> personElements = XML.getElementsByTagName(parent, AtomNS.AUTHOR);
		List<AtomPersonConstruct> persons = new ArrayList<AtomPersonConstruct>();
		for (Element element : personElements) {
			persons.add(new AtomPersonConstruct(element));
		}
		return persons;
	}
	
	
	
	public static void setAuthors(Element parent, List<AtomPersonConstruct> authors) {

		// remove existing category elements
		XML.removeElementsByTagName(parent, AtomNS.AUTHOR);
		
		// create new category elements and append to entry element
		for (AtomPersonConstruct author : authors) {
			Element authorElement = XML.createElement(parent, AtomNS.AUTHOR);
			author.populate(authorElement);
		}
		
	}
	
}
