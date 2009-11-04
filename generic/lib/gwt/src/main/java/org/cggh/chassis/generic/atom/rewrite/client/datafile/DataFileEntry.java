/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client.datafile;

import java.util.List;

import org.cggh.chassis.generic.atom.rewrite.client.AtomEntry;
import org.cggh.chassis.generic.atom.rewrite.client.AtomLink;


/**
 * @author aliman
 *
 */
public interface DataFileEntry extends AtomEntry {

	public void addFileRevisionLink(String href);
	public List<AtomLink> getFileRevisionLinks();
	
}
