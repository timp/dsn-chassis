/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.base.perspective;

/**
 * @author aliman
 *
 */
public interface PerspectiveModelListener {

	void onIsCurrentPerspectiveChanged(boolean from, boolean to);

	void onMainWidgetChanged(String from, String to);

}
