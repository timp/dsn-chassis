/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;

/**
 * @author aliman
 *
 */
public interface DatasetLink extends AtomLink {

	public DatasetEntry getEntry();
	
}
