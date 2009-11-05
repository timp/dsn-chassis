/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import java.util.List;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomLink;


/**
 * @author aliman
 *
 */
public interface DataFileEntry extends AtomEntry {

	public void addFileRevisionLink(String href);
	public List<AtomLink> getFileRevisionLinks();
	
}
