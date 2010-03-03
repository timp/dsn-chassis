/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyWidgetRenderer;

/**
 * @author aliman
 *
 */
public class CustomViewStudyWidget extends ViewStudyWidget {

	
	
	
	@Override
	protected ViewStudyWidgetRenderer createRenderer() {
		return new CustomViewStudyWidgetRenderer(this);
	}

	
	
	
}
