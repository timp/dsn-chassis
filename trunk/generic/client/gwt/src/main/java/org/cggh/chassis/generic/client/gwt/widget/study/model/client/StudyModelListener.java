/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.model.client;


/**
 * @author raok
 *
 */
public interface StudyModelListener {

	void onTitleChanged(String before, String after, Boolean isValid);

	void onSummaryChanged(String before, String after, Boolean isValid);

	void onAcceptClinicalDataChanged(Boolean before, Boolean after, Boolean isValid);

	void onAcceptMolecularDataChanged(Boolean before, Boolean after, Boolean isValid);

	void onAcceptInVitroDataChanged(Boolean before, Boolean after, Boolean isValid);

	void onAcceptPharmacologyDataChanged(Boolean before, Boolean after, Boolean isValid);

	void onStudyEntryChanged(Boolean isValid);

	void onStatusChanged(Integer before, Integer after);

}
