/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol;

import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public interface AtomXQueryService {

	public Deferred<String> query(String xquery);
	
}
