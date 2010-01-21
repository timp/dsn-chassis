/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.media.MediaEntry;

/**
 * @author aliman
 *
 */
public interface RevisionLink extends AtomLink {

	public MediaEntry getEntry();
	
}
