/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

/**
 * @author raok
 *
 */
public interface CreateStudyWidgetModelListener {

	void onTitleChanged(String before, String after);

	void onSummaryChanged(String before, String after);

	void onAcceptClinicalDataChanged(Boolean before, Boolean after);

	void onAcceptMolecularDataChanged(Boolean before, Boolean after);

	void onAcceptInVitroDataChanged(Boolean before, Boolean after);

	void onAcceptPharmacologyDataChanged(Boolean before, Boolean after);

	void onStatusChanged(Integer before, Integer after);
	
	
}
