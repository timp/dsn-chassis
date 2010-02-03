/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import org.cggh.chassis.generic.atom.client.AtomFeedImpl;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DatasetFeedImpl 
	extends AtomFeedImpl<DatasetEntry, DatasetFeed> 
	implements DatasetFeed {

	
	
	/**
	 * @param feedElement
	 * @param factory
	 */
	protected DatasetFeedImpl(Element e, DatasetFactory factory) {
		super(e, factory);
	}

	
	
}
