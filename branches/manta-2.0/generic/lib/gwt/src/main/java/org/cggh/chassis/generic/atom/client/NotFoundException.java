/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

/**
 * @author aliman
 *
 */
public class NotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;

	public NotFoundException(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return this.url;
	}
	
}
