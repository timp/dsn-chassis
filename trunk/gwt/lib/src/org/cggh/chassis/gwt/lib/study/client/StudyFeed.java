/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client;

import java.util.List;
import org.cggh.chassis.gwt.lib.atom.client.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.AtomFormatException;

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
		return StudyEntry.getStudyEntries(this.feedElement);
	}
		
}
