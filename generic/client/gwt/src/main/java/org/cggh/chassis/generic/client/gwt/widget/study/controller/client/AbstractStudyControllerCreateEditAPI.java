package org.cggh.chassis.generic.client.gwt.widget.study.controller.client;

public abstract interface AbstractStudyControllerCreateEditAPI {

	public void updateTitle(String title);

	public void updateSummary(String summary);

	public void updateAcceptClinicalData(Boolean acceptClinicalData);

	public void updateAcceptMolecularData(Boolean acceptMolecularData);

	public void updateAcceptInVitroData(Boolean acceptInVitroData);

	public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData);

	public void cancelSaveOrUpdateStudyEntry();

}