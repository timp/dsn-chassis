/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomContent {

	protected String type = null;
	protected String src = null;
	protected String inlineContent = null;

	public AtomContent() {}
	
	public AtomContent(String type, String src, String inlineContent) {
		this.type = type;
		this.src = src;
		this.inlineContent = inlineContent;
	}
	
	/**
	 * @param contentElement
	 */
	public AtomContent(Element contentElement) {
		this.type = contentElement.getAttribute(AtomNS.TYPE);
		this.src = contentElement.getAttribute(AtomNS.SRC);
		if (this.type.equals("application/xml") || this.type.endsWith("+xml")) {
			this.inlineContent = contentElement.toString(); // TODO fix this
		}
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return this.src;
	}

	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the inlineContent
	 */
	public String getInlineContent() {
		return this.inlineContent;
	}

	/**
	 * @param inlineContent the inlineContent to set
	 */
	public void setInlineContent(String inlineContent) {
		this.inlineContent = inlineContent;
	}

	public String toXML() {
		String inlineContent = this.getInlineContent();
		String type = this.getType();
		String src = this.getSrc();
		
		if (type != null && inlineContent != null) {
			return "<content type=\""+type+"\">"+inlineContent+"</content>";
		}
		else if (type != null && src != null) {
			return "<content type=\""+type+"\" src=\""+src+"\"/>";
		}
		else {
			return ""; // TODO check the specs
		}
	}
	
}
