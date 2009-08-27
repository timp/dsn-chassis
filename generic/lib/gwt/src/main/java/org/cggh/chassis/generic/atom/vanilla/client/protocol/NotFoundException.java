/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol;

/**
 * @author aliman
 *
 */
public class NotFoundException {

	private String url;

	public NotFoundException(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return this.url;
	}
	
}
