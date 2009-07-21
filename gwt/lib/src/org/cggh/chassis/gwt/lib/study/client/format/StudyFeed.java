/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client.format;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class StudyFeed extends AtomFeed {


	
	public StudyFeed() throws AtomFormatException {
		super(template, new StudyFactory());
	}
	
	
	
	public StudyFeed(AtomFactory factory) throws AtomFormatException {
		super(template, factory);
	}
	
	
	public StudyFeed(String feedDocXML) throws AtomFormatException {
		super(feedDocXML, new StudyFactory());
	}
	
	

	public StudyFeed(String feedDocXML, AtomFactory factory) throws AtomFormatException {
		super(feedDocXML, factory);
	}
	
	
	
	public static StudyFeed as(AtomFeed feed) {
		if (feed instanceof StudyFeed) {
			return (StudyFeed) feed;
		}
		else {
			return null;
		}
	}

	
	
}
