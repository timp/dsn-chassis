/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client;

/**
 * @author aliman
 *
 */
public interface AtomAuthor extends ElementWrapper {

	public String getURI();
	public void setURI(String uri);
	public String getEmail();
	public void setEmail(String email);
	public String getName();
	public void setName(String name);
	
}
