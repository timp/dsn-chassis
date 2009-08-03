/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;

/**
 * @author aliman
 *
 */
interface ModelListener {

	void onIsCurrentPerspectiveChanged(boolean from, boolean to);

	void onMainWidgetChanged(String from, String to);

}
