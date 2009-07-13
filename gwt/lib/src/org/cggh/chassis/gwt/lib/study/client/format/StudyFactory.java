/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client.format;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;

import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyFactory extends AtomFactory {

	@Override
	public AtomFeed createFeed(String feedDocXML) throws AtomFormatException {
		return new StudyFeed(feedDocXML, this);
	}
	
	@Override
	public AtomEntry createEntry(String entryDocXML) throws AtomFormatException {
		return new StudyEntry(entryDocXML);
	}
	
	@Override
	public AtomEntry createEntry(Element entryElement) throws AtomFormatException {
		return new StudyEntry(entryElement);
	}
	
}
