/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;


/**
 * @author aliman
 *
 */
public interface DataFileEntry extends AtomEntry {

	public List<RevisionLink> getRevisionLinks();
	
}
