/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.format;

import java.util.List;
import org.cggh.chassis.gwt.lib.xml.client.XML;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AtomFeed {

	
	
	protected Element feedElement = null;
	protected AtomFactory factory = null;

	
	protected static final String template = "<feed xmlns=\"http://www.w3.org/2005/AtomNS\"></feed>";
	
	
	
	public AtomFeed() throws AtomFormatException {
		init(template);
	}
	
	
	
	public AtomFeed(String feedDocXML) throws AtomFormatException {
		init(feedDocXML);
	}
	
	
	
	/**
	 * @param feedDocXML
	 * @param atomFactory
	 * @throws AtomFormatException 
	 */
	public AtomFeed(String feedDocXML, AtomFactory atomFactory) throws AtomFormatException {
		this.factory = atomFactory;
		init(feedDocXML);
	}



	public static AtomFeed newInstance(String feedDocXML) throws AtomFormatException {
		return new AtomFeed(feedDocXML);
	}
	
	
	
	protected void init(String feedDocXML) throws AtomFormatException {

		if (this.factory == null) {
			this.factory = new AtomFactory();
		}
		
		Document doc = null;
		
		try {
			doc = XMLParser.parse(feedDocXML);
		} catch (Throwable ex) {
			throw new AtomFormatException("could not parse XML: "+ex.getLocalizedMessage(), ex);
		}

		this.feedElement = XML.getElementByTagNameNS(doc, AtomNS.NS, AtomNS.FEED); // ns-aware here only
		
		if (this.feedElement == null) {
			throw new AtomFormatException("feed element is null");
		}
		if (!this.feedElement.getTagName().equals(AtomNS.FEED)) {
			throw new AtomFormatException("feed element has unexpected tag name: "+this.feedElement.getTagName());
		}
		if (this.feedElement.getNamespaceURI() == null) {
			throw new AtomFormatException("feed element namespace URI is null");
		}
		if (!this.feedElement.getNamespaceURI().equals(AtomNS.NS)) {
			throw new AtomFormatException("feed element has unexpected namespace URI: "+this.feedElement.getNamespaceURI());
		}
	}
	
	
	
	public String getId() {
		return XML.getElementSimpleContentByTagName(this.feedElement, AtomNS.ID);
	}
	
	
	
	public String getUpdated() {
		return XML.getElementSimpleContentByTagName(this.feedElement, AtomNS.UPDATED);		
	}
	
	
	
	public String getTitle() {
		return XML.getElementSimpleContentByTagName(this.feedElement, AtomNS.TITLE);
	}
	
	

	public void setTitle(String title) {
		XML.setElementSimpleContentByTagName(this.feedElement, AtomNS.TITLE, title);
	}
	
	
	
	public List<AtomEntry> getEntries() throws AtomFormatException {
		return this.factory.createEntries(this.feedElement);
	}
}
