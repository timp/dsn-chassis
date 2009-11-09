/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.datafile.client.protocol;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

import org.cggh.chassis.generic.async.client.Deferred;

/**
 * @author aliman
 *
 */
public interface DataFileQueryService {

	public Deferred<AtomFeed> query(DataFileQuery query);
	
}
