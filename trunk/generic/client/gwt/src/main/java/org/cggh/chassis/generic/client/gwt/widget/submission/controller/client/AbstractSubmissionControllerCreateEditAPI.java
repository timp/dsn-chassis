package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

public interface AbstractSubmissionControllerCreateEditAPI {

	public abstract void updateTitle(String title);

	public abstract void updateSummary(String summary);

	public abstract void updateAcceptClinicalData(Boolean acceptClinicalData);

	public abstract void updateAcceptMolecularData(Boolean acceptMolecularData);

	public abstract void updateAcceptInVitroData(Boolean acceptInVitroData);

	public abstract void updateAcceptPharmacologyData(
			Boolean acceptPharmacologyData);

	public abstract void addStudyLink(String studyEntryURL);

	public abstract void removeStudyLink(String studyEntryURL);

	public abstract void cancelSaveOrUpdateSubmissionEntry();

}