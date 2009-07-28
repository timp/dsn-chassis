/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

/**
 * @author aliman
 *
 */
public interface ApplicationEventListener {

	public void onInitialisationSuccess();
	public void onInitialisationFailure(String message);
	
}
