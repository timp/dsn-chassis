/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import java.util.ArrayList;
import java.util.List;
import org.cggh.chassis.gwt.lib.atom.client.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.AtomFormatException;
import org.cggh.chassis.gwt.lib.atom.client.AtomNS;
import org.cggh.chassis.gwt.lib.xml.client.XML;
import com.google.gwt.xml.client.Element;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyFeed extends AtomFeed {


	
	public StudyFeed() throws AtomFormatException {
		super();
	}
	
	
	
	public StudyFeed(String feedDocXML) throws AtomFormatException {
		super(feedDocXML);
	}
	
	
	
	public List<StudyEntry> getStudyEntries() throws AtomFormatException {
		List<Element> entryElements = XML.getElementsByTagNameNS(this.feedElement, AtomNS.NS, AtomNS.ENTRY);
		List<StudyEntry> entries = new ArrayList<StudyEntry>();
		for (Element entryElement : entryElements) {
			StudyEntry entry = new StudyEntry(entryElement);
			entries.add(entry);
		}
		return entries;
	}
		
}
