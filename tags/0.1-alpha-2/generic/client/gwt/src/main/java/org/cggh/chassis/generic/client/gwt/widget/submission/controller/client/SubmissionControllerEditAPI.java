package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;

public interface SubmissionControllerEditAPI {

	public void updateTitle(String title);

	public void updateSummary(String summary);

	public void updateAcceptClinicalData(Boolean acceptClinicalData);

	public void updateAcceptMolecularData(Boolean acceptMolecularData);

	public void updateAcceptInVitroData(Boolean acceptInVitroData);

	public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData);

	public void addStudyLink(String studyEntryURL);

	public void removeStudyLink(String studyEntryURL);

	public void cancelSaveOrUpdateSubmissionEntry();

	public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad);

	public void loadSubmissionEntryByURL(String submissionEntryURL);

	public void updateSubmissionEntry();

}