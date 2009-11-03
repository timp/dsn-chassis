/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.format.impl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
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
public class SubmissionFeedImpl extends AtomFeedImpl implements SubmissionFeed {

	/**
	 * @param feedElement
	 * @param factory
	 */
	protected SubmissionFeedImpl(Element feedElement, SubmissionFactory factory) {
		super(feedElement, factory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed#getSubmissionEntries()
	 */
	public List<SubmissionEntry> getSubmissionEntries() {

		List<SubmissionEntry> entries = new ArrayList<SubmissionEntry>();
		
		Function<Element,SubmissionEntry> wrapper = new Function<Element,SubmissionEntry>() {

			public SubmissionEntry apply(Element in) {
				return (SubmissionEntry) factory.createEntry(in);
			}
			
		};

		Functional.map(XML.getElementsByTagName(element, Atom.ELEMENT_ENTRY), entries, wrapper);
		
		return entries;
	}

}
