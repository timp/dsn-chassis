/**
 * 
 */
package org.cggh.chassis.generic.xml.client;



import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class ElementWrapperImpl implements ElementWrapper {

	protected Element element;

	protected ElementWrapperImpl(Element e) {
		this.element = e;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.ElementWrapper#getElement()
	 */
	public Element getElement() {
		return this.element;
	}
	
	public boolean equals(Object another) {
		if (another instanceof ElementWrapperImpl) {
			ElementWrapperImpl ew = (ElementWrapperImpl) another;
			return element.equals(ew.getElement());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return element.toString();
	}

}
