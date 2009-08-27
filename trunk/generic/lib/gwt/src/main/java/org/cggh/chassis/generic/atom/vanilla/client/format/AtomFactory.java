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

	public AtomFeed createFeed(String feedDocument) throws AtomFormatException;
	public AtomEntry createEntry();
	public AtomEntry createEntry(String entryDocument) throws AtomFormatException;
	public AtomEntry createEntry(Element entryElement) throws AtomFormatException;
	
}
