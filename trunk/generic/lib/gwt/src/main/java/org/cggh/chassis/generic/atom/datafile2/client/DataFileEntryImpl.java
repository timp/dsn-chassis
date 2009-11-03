/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile2.client;

import java.util.List;

import org.cggh.chassis.generic.atom.rewrite.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.rewrite.client.AtomLink;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class DataFileEntryImpl extends AtomEntryImpl implements DataFileEntry {
	
	
	
	
	private Log log = LogFactory.getLog(DataFileEntryImpl.class);

	
	
	
	/**
	 * @param e
	 * @param factory
	 */
	public DataFileEntryImpl(Element e, DataFileFactory factory) {
		super(e, factory);
	}

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.datafile2.client.format.DataFileEntry#addFileRevisionLink(java.lang.String)
	 */
	public void addFileRevisionLink(String href) {
		log.enter("addFileRevisionLink");
		
		// TODO Auto-generated method stub
		
		log.leave();
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.datafile2.client.format.DataFileEntry#getFileRevisionLinks()
	 */
	public List<AtomLink> getFileRevisionLinks() {
		log.enter("getFileRevisionLinks");
		
		// TODO Auto-generated method stub
		
		log.leave();
		return null;
	}
	
	

}
