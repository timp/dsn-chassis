/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

/**
 * @author raok
 *
 */
public interface PerspectiveWidgetModelListener {

	void onDisplayStatusChanged(Integer before, Integer after);

	void userMightLoseChanges(Integer userRequestedState);

}
