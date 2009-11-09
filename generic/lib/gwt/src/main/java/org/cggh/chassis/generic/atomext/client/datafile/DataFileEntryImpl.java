/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntryImpl;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.shared.Chassis;
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

	
	

	public List<RevisionLink> getRevisionLinks() {
		log.enter("getFileRevisionLinks");
		
		List<RevisionLink> links = new ArrayList<RevisionLink>();
		for (AtomLink l : this.getLinks()) {
			if (l.getRel().equals(Chassis.Rel.REVISION)) {
				links.add(RevisionLinkImpl.as(l));
			}
		}
		
		log.leave();
		return links;
	}
	
	

}
