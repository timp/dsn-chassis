/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class MockAtomLink implements AtomLink {

	private String href;
	private String hreflang;
	private String length;
	private String rel;
	private String title;
	private String type;
	private MockAtomFactory factory;
	
	/**
	 * @param mockAtomFactory
	 */
	protected MockAtomLink(MockAtomFactory factory) {
		this.factory = factory;
	}
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * @return the hreflang
	 */
	public String getHrefLang() {
		return hreflang;
	}
	/**
	 * @param hreflang the hreflang to set
	 */
	public void setHrefLang(String hreflang) {
		this.hreflang = hreflang;
	}
	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @return the rel
	 */
	public String getRel() {
		return rel;
	}
	/**
	 * @param rel the rel to set
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @param link
	 */
	public void put(AtomLink link) {
		this.href = link.getHref();
		this.hreflang = link.getHrefLang();
		this.length = link.getLength();
		this.rel = link.getRel();
		this.title = link.getTitle();
		this.type = link.getType();
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.ElementWrapper#getElement()
	 */
	public Element getElement() {
		// not needed because mock
		return null;
	}

}
