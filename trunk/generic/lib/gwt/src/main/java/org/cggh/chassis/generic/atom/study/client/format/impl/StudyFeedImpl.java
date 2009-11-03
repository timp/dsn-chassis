/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.format.impl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeedImpl;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.Functional;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class StudyFeedImpl extends AtomFeedImpl implements StudyFeed {

	/**
	 * @param feedElement
	 * @param factory
	 */
	protected StudyFeedImpl(Element feedElement, StudyFactory factory) {
		super(feedElement, factory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyFeed#getStudyEntries()
	 */
	public List<StudyEntry> getStudyEntries() {
		
		List<StudyEntry> entries = new ArrayList<StudyEntry>();
		
		Function<Element,StudyEntry> wrapper = new Function<Element,StudyEntry>() {

			public StudyEntry apply(Element in) {
				return (StudyEntry) factory.createEntry(in);
			}
			
		};

		Functional.map(XML.getElementsByTagName(element, Atom.ELEMENT_ENTRY), entries, wrapper);
		
		return entries;
	}

}
