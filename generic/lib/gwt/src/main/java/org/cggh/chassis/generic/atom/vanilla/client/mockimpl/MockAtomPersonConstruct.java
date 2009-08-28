/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomPersonConstruct;

/**
 * @author aliman
 *
 */
public class MockAtomPersonConstruct implements AtomPersonConstruct {

	private MockAtomFactory factory;
	private String email;
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
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
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the uri
	 */
	public String getURI() {
		return uri;
	}



	/**
	 * @param uri the uri to set
	 */
	public void setURI(String uri) {
		this.uri = uri;
	}



	private String name;
	private String uri;

	/**
	 * @param mockAtomFactory
	 */
	MockAtomPersonConstruct(MockAtomFactory factory) {
		this.factory = factory;
	}



	/**
	 * @param person
	 */
	public void put(AtomPersonConstruct person) {
		this.name = person.getName();
		this.email = person.getEmail();
		this.uri = person.getURI();
	}

}
