/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

/**
 * @author aliman
 *
 */
interface ModelListener {

	void onIsCurrentPerspectiveChanged(boolean wasCurrent, boolean current);

	void onMainWidgetChanged(String from, String to);

}
