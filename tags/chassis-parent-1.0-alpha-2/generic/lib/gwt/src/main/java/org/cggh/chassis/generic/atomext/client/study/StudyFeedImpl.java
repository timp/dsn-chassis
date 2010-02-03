/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atom.client.AtomFactory;
import org.cggh.chassis.generic.atom.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class StudyFeedImpl 
	extends AtomFeedImpl<StudyEntry, StudyFeed>
	implements StudyFeed {

	/**
	 * @param feedElement
	 * @param factory
	 */
	protected StudyFeedImpl(Element feedElement, AtomFactory<StudyEntry, StudyFeed> factory) {
		super(feedElement, factory);
	}
	

}
