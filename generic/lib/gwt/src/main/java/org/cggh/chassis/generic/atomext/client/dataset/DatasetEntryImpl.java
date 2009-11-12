/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DatasetEntryImpl extends AtomEntryImpl implements DatasetEntry {
	/**
	 * @param e
	 * @param factory
	 */
	protected DatasetEntryImpl(Element e, DatasetFactory factory) {
		super(e, factory);
	}

	private Log log = LogFactory.getLog(DatasetEntryImpl.class);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#getDataFileLinks()
	 */
	public List<DataFileLink> getDataFileLinks() {
		log.enter("getDataFileLinks");
		
		// TODO Auto-generated method stub
		
		log.leave();
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry#getStudyLinks()
	 */
	public List<StudyLink> getStudyLinks() {
		log.enter("getStudyLinks");
		
		// TODO Auto-generated method stub
		
		log.leave();
		return null;
	}

}
