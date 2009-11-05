/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomCategory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class MockAtomCategory implements AtomCategory {

	private String label;
	private String term;
	private String scheme;
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return scheme;
	}
	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.ElementWrapper#getElement()
	 */
	public Element getElement() {
		// do nothing because mock
		return null;
	}
	


}
