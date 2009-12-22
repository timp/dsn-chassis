/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class AssignCuratorWidget 
	extends DelegatingWidget<AssignCuratorWidgetModel, AssignCuratorWidgetRenderer> {

	
	
	private Log log = LogFactory.getLog(AssignCuratorWidget.class);
	
	
	
	@Override
	protected AssignCuratorWidgetModel createModel() {
		return new AssignCuratorWidgetModel();
	}

	
	
	
	@Override
	protected AssignCuratorWidgetRenderer createRenderer() {
		return new AssignCuratorWidgetRenderer();
	}


}
