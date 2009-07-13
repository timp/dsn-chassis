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
public class AtomPersonConstruct {

	private String uri;
	private String email;
	private String name;

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
	protected AtomPersonConstruct(Element authorElement) {
		this.name = XML.getElementSimpleContentByTagName(authorElement, AtomNS.NAME);
		this.email = XML.getElementSimpleContentByTagName(authorElement, AtomNS.EMAIL);
		this.uri = XML.getElementSimpleContentByTagName(authorElement, AtomNS.URI);
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
