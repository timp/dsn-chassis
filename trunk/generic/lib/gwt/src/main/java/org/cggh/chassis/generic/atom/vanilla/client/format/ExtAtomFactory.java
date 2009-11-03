/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.format;


import com.google.gwt.xml.client.Element;


/**
 * @author aliman
 *
 */
public interface ExtAtomFactory<E extends AtomEntry, F extends ExtAtomFeed<E>> {

	public AtomAuthor createAuthor();
	public AtomAuthor createAuthor(Element authorElement);
	public AtomLink createLink();
	public AtomLink createLink(Element linkElement);
	public AtomCategory createCategory();
	public AtomCategory createCategory(Element categoryElement);

	public F createFeed();
	public F createFeed(String feedDocument);
	public E createEntry();
	public E createEntry(String entryDocument);
	public E createEntry(Element entryElement);

}
