/**
 * 
 */
package org.cggh.chassis.generic.miniatom.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AtomHelper {

	
	
	public static String getId(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_ID, Atom.NSURI);
	}
	
	
	
	private static String getPublished(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_PUBLISHED, Atom.NSURI);
	}
	
	public static String getPublishedAsDate(Element parent) {
		return timestampAsDate(getPublished(parent));
	}
	
	public static String getPublishedAsTime(Element parent) {
		return timestampToTheMinute(getPublished(parent));
	}
	
	
	
	private static String getUpdated(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_UPDATED, Atom.NSURI);
	}
	
	public static String getUpdatedAsDate(Element parent) {
		return timestampAsDate(getUpdated(parent));
	}
	
	public static String getUpdatedAsTime(Element parent) {
		return timestampToTheMinute(getUpdated(parent));
	}
	
	
	
	public static String getTitle(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_TITLE, Atom.NSURI);
	}
	
	
	
	public static void setTitle(Element parent, String title) {
		XMLNS.setSingleChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_TITLE, Atom.PREFIX, Atom.NSURI, title);
	}

	
	
	public static String getSummary(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_SUMMARY, Atom.NSURI);
	}
	
	public static void setSummary(Element parent, String summary) {
		XMLNS.setSingleChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_SUMMARY, Atom.PREFIX, Atom.NSURI, summary);
	}

	// TODO: This shouldn't be Atom (not part of the standard).
	public static String getMediaResourceSize(Element parent) {
		// Return the file size with appropriate units, e.g. 10 bytes, 10 KB or 10 MB, etc.  
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_SIZE, Atom.NSURI);
	}

	
	public static List<Element> getLinks(Element parent) {
		return XMLNS.getChildrenByTagNameNS(parent, Atom.ELEMENT_LINK, Atom.NSURI);
	}
	
	
	
	public static List<Element> getLinks(Element parent, String rel) {
		List<Element> links = new ArrayList<Element>();
		for (Element link : getLinks(parent)) {
			String relValue = getRelAttr(link);
			if ( (relValue == null && rel == null) || (relValue != null && rel != null && rel.equals(relValue))) {
				links.add(link);
			}
		}
		return links;
	}
	
	
	
	public static Element getEditLink(Element parent) {
		List<Element> links = getLinks(parent, Atom.REL_EDIT);
		if (links.size() == 0) {
			return null;
		}
		else {
			return links.get(0);
		}
	}
	
	
	
	public static String getEditLinkHrefAttr(Element parent) {
		return getHrefAttr(getEditLink(parent));
	}
	
	
	
	
	public static Element getEditMediaLink(Element parent) {
		List<Element> links = getLinks(parent, Atom.REL_EDIT_MEDIA);
		if (links.size() == 0) {
			return null;
		}
		else {
			return links.get(0);
		}
	}
	
	
	
	
	public static String getRelAttr(Element e) {
		return e.getAttribute(Atom.ATTR_REL);
	}
	
	
	
	public static String getHrefAttr(Element e) {
		return e.getAttribute(Atom.ATTR_HREF);
	}
	
	
	
	public static String getTypeAttr(Element e) {
		return e.getAttribute(Atom.ATTR_TYPE);
	}
	
	
	
	
	public static void addLink(Element parent, String rel, String href) {
		Element linkElement = XMLNS.createElementNS(Atom.ELEMENT_LINK, Atom.PREFIX, Atom.NSURI);
		if (rel != null) 
			linkElement.setAttribute(Atom.ATTR_REL, rel);
		if (href != null) 
			linkElement.setAttribute(Atom.ATTR_HREF, href);
		parent.appendChild(linkElement);
	}
	
	
	
	
	public static List<Element> getAuthors(Element parent) {
		return XMLNS.getChildrenByTagNameNS(parent, Atom.ELEMENT_AUTHOR, Atom.NSURI);
	}
	
	
	public static List<String> getAuthorEmails(Element parent) {
		List<String> them = new ArrayList<String>();
		for (Element author : getAuthors(parent)) 
			them.add(getEmail(author));
		return them;
	}
	
	

	public static void addAuthor(Element parent, String name, String email, String uri) {
		Element author = XMLNS.createElementNS(Atom.ELEMENT_AUTHOR, Atom.PREFIX, Atom.NSURI);
		if (name != null)
			XMLNS.setSingleChildSimpleContentByTagNameNS(author, Atom.ELEMENT_NAME, Atom.PREFIX, Atom.NSURI, name);
		if (email != null)
			XMLNS.setSingleChildSimpleContentByTagNameNS(author, Atom.ELEMENT_EMAIL, Atom.PREFIX, Atom.NSURI, email);
		if (uri != null)
			XMLNS.setSingleChildSimpleContentByTagNameNS(author, Atom.ELEMENT_URI, Atom.PREFIX, Atom.NSURI, uri);
		parent.appendChild(author);
	}
	
	
	
	
	public static void addAuthor(Element parent, String email) {
		addAuthor(parent, null, email, null);
	}
	
	
	
	
	public static void removeAuthors(Element parent) {
		XMLNS.removeChildrenByTagNameNS(parent, Atom.ELEMENT_AUTHOR, Atom.NSURI);
	}
	
	
	
	
	public static void setAuthors(Element parent, List<String> emails) {
		removeAuthors(parent);
		for (String email : emails) {
			addAuthor(parent, email);
		}
	}

	
	
	
	public static String getName(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_NAME, Atom.NSURI);
	}
	

	
	
	public static String getEmail(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_EMAIL, Atom.NSURI);
	}

	
	
	
	public static String getUri(Element parent) {
		return XMLNS.getFirstChildSimpleContentByTagNameNS(parent, Atom.ELEMENT_URI, Atom.NSURI);
	}
	

	
	
	public static List<Element> getCategories(Element parent) {
		return XMLNS.getChildrenByTagNameNS(parent, Atom.ELEMENT_CATEGORY, Atom.NSURI);
	}
	
	
	
	public static List<Element> getCategories(Element parent, String scheme) {
		List<Element> categories = new ArrayList<Element>();
		for (Element categoryElement : getCategories(parent)) {
			String schemeValue = getSchemeAttr(categoryElement);
			if ( (scheme == null && schemeValue == null) || (scheme != null && schemeValue != null && scheme.equals(schemeValue)) ) {
				categories.add(categoryElement);
			}
		}
		return categories;
	}
	
	
	
	public static Element getFirstCategory(Element parent, String scheme) {
		List<Element> categories = getCategories(parent, scheme);
		return (categories.size() > 0) ? categories.get(0) : null;
	}
	
	
	
	public static void addCategory(Element parent, String scheme, String term, String label) {
		Element categoryElement = XMLNS.createElementNS(Atom.ELEMENT_CATEGORY, Atom.PREFIX, Atom.NSURI);
		if (scheme != null)
			categoryElement.setAttribute(Atom.ATTR_SCHEME, scheme);
		if (term != null)
			categoryElement.setAttribute(Atom.ATTR_TERM, term);
		if (label != null)
			categoryElement.setAttribute(Atom.ATTR_LABEL, label);
		parent.appendChild(categoryElement);
	}

	
	
	public static void addCategory(Element parent, String scheme, String term) {
		addCategory(parent, scheme, term, null);
	}
	
	
	
	public static String getSchemeAttr(Element e) {
		return e.getAttribute(Atom.ATTR_SCHEME);
	}
	
	
	
	
	public static String getTermAttr(Element e) {
		return e.getAttribute(Atom.ATTR_TERM);
	}
	
	
	
	
	public static String getLabelAttr(Element e) {
		return e.getAttribute(Atom.ATTR_LABEL);
	}
	

	
	
	public static Element getContent(Element parent) {
		return XMLNS.getFirstChildByTagNameNS(parent, Atom.ELEMENT_CONTENT, Atom.NSURI);
	}
	
	
	
	public static void removeContent(Element parent) {
		XMLNS.removeChildrenByTagNameNS(parent, Atom.ELEMENT_CONTENT, Atom.NSURI);
	}
	
	
	
	
	public static void setContent(Element parent, Element content, String type) {
		removeContent(parent);
		Element contentElement = XMLNS.createElementNS(Atom.ELEMENT_CONTENT, Atom.PREFIX, Atom.NSURI);
		if (type != null)
			contentElement.setAttribute(Atom.ATTR_TYPE, type);
		parent.appendChild(contentElement);
		parent.getOwnerDocument().importNode(content, true);
		contentElement.appendChild(content);
	}

	
	
	
	public static void setContent(Element parent, Element content) {
		setContent(parent, content, "application/xml");
	}



	public static List<Element> getEntries(Element parent) {
		return XMLNS.getChildrenByTagNameNS(parent, Atom.ELEMENT_ENTRY, Atom.NSURI);
	}
	
	
	
	
	public static Element getFirstEntry(Element parent) {
		List<Element> entries = getEntries(parent);
		if (entries.size() > 0) return entries.get(0);
		else return null;
	}
	


	public static Document createEntryDoc() {
		return XMLNS.createDocumentNS(Atom.ELEMENT_ENTRY, Atom.PREFIX, Atom.NSURI);
	}


	/** 2010-03-22T20:55:07+00:00 becomes 2010-03-22 */
	public static String timestampAsDate(String timestamp) { 
		return timestamp.substring(0,10); 
	}
	
	/** 2010-03-22T20:55:07+00:00 becomes 2010-03-22 20:55 */
	public static String timestampToTheMinute(String timestamp) { 
		return timestamp.substring(0,16).replace("T", " "); 
	}

	
	
}
