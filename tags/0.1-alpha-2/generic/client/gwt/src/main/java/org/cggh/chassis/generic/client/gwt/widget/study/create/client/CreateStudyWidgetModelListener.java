/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

/**
 * @author raok
 *
 */
public interface CreateStudyWidgetModelListener {

	void onStatusChanged(Integer before, Integer after);

	void onTitleChanged(Boolean isValid);

	void onSummaryChanged(Boolean isValid);

	void onModulesChanged(Boolean isValid);

	void onFormCompleted(Boolean isFormComplete);
	
	
}
