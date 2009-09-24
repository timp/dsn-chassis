package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

public abstract interface AbstractSubmissionControllerCreateEditAPI {

	public void updateTitle(String title);

	public void updateSummary(String summary);

	public void updateAcceptClinicalData(Boolean acceptClinicalData);

	public void updateAcceptMolecularData(Boolean acceptMolecularData);

	public void updateAcceptInVitroData(Boolean acceptInVitroData);

	public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData);

	public void addStudyLink(String studyEntryURL);

	public void removeStudyLink(String studyEntryURL);

	public void cancelCreateOrUpdateSubmissionEntry();

}