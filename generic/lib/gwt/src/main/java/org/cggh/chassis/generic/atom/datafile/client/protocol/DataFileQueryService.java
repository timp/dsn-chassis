/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile.client.protocol;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public interface DataFileQueryService {

	public Deferred<AtomFeed> query(DataFileQuery query);
	
}
