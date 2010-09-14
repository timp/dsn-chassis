/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.widget.study.client.sq.ViewStudyQuestionnaireWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.sq.ViewStudyQuestionnaireWidgetRenderer;

/**
 * @author aliman
 *
 */
public class CustomViewStudyQuestionnaireWidget 
	extends ViewStudyQuestionnaireWidget {

	
	
	@Override
	protected ViewStudyQuestionnaireWidgetRenderer createRenderer() {
		return new CustomViewStudyQuestionnaireWidgetRenderer(this);
	}



}
