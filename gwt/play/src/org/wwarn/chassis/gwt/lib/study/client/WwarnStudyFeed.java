/**
 * $Id$
 */
package org.wwarn.chassis.gwt.lib.study.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomFactory;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFeed;
import org.cggh.chassis.gwt.lib.atom.client.format.AtomFormatException;
import org.cggh.chassis.gwt.lib.study.client.format.StudyFeed;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class WwarnStudyFeed extends StudyFeed {

	
	
	/**
	 * @throws AtomFormatException
	 */
	public WwarnStudyFeed() throws AtomFormatException {
		super(new WwarnStudyFactory());
	}

	
	
	/**
	 * @param feedDocXML
	 * @throws AtomFormatException
	 */
	public WwarnStudyFeed(String feedDocXML) throws AtomFormatException {
		super(feedDocXML, new WwarnStudyFactory());
	}

	
	
	public WwarnStudyFeed(String feedDocXML, AtomFactory factory) throws AtomFormatException {
		super(feedDocXML, factory);
	}
	
	
	
	public static WwarnStudyFeed as(AtomFeed feed) {
		if (feed instanceof WwarnStudyFeed) {
			return (WwarnStudyFeed) feed;
		}
		else {
			return null;
		}
	}

	
	
	
}
