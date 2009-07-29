/**
 * 
 */
package org.cggh.chassis.gwt.lib.twisted.client;

/**
 * @author aliman
 *
 */
public class GenericError extends Error {

	private Object res;


	public GenericError(Object res) {
		this.res = res;
	}
	
	public Object getResult() {
		return this.res;
	}
	

	private static final long serialVersionUID = 1L;

}
