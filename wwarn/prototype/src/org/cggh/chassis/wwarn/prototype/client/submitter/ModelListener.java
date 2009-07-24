/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter;

/**
 * @author aliman
 *
 */
interface ModelListener {

	void onStateTokenChanged(String oldState, String stateToken);

	void onIsCurrentPerspectiveChanged(boolean wasCurrent, boolean current);

}
