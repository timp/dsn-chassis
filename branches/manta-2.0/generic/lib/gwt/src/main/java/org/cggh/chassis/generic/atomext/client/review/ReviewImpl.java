/**
 * @author timp
 * @since 4 Dec 2009
 */
package org.cggh.chassis.generic.atomext.client.review;

import org.cggh.chassis.generic.xml.client.ElementWrapperImpl;

import com.google.gwt.xml.client.Element;

public class ReviewImpl 
    extends ElementWrapperImpl implements Review {

	private final static String ACCEPT = "Accept";
	
	private String outcome;

	protected ReviewImpl(Element e) {
		super(e);
		this.outcome = ACCEPT;
	}


	public void setOutcome(String outcome) {
		
		// TODO store this value in the wrapped XML, not in a field of the object
		
		this.outcome = outcome;
		
	}

	public String getOutcome() {
		
		// TODO retrieve this value from the wrapped XML
		
		return outcome;
	}

	
}
