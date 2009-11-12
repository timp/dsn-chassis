/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.dataset;

import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;

/**
 * @author aliman
 *
 */
public interface DataFileLink extends AtomLink {

	public DataFileEntry getEntry();
	
}
