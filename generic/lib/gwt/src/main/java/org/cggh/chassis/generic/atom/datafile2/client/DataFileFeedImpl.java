/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile2.client;

import org.cggh.chassis.generic.atom.rewrite.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;



/**
 * @author aliman
 *
 */
public class DataFileFeedImpl 
	extends AtomFeedImpl<DataFileEntry, DataFileFeed> 
	implements DataFileFeed {

	
	
	
	/**
	 * @param feedElement
	 * @param factory
	 */
	public DataFileFeedImpl(Element feedElement, DataFileFactory factory) {
		super(feedElement, factory);
	}


	
	
}
