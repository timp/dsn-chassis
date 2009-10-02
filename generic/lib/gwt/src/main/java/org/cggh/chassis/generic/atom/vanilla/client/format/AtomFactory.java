/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public interface AtomFactory {

	public AtomFeed createFeed();
	public AtomFeed createFeed(String feedDocument);
	public AtomEntry createEntry();
	public AtomEntry createEntry(String entryDocument);
	public AtomEntry createEntry(Element entryElement);
	public AtomAuthor createAuthor();
	public AtomAuthor createAuthor(Element authorElement);
	public AtomLink createLink();
	public AtomLink createLink(Element linkElement);
	public AtomCategory createCategory();
	public AtomCategory createCategory(Element categoryElement);

}