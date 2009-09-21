package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

public interface SubmissionControllerCreateAPI {

	public void setUpNewSubmission(String feedURL);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateTitle(java.lang.String)
	 */
	public void updateTitle(String title);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateSummary(java.lang.String)
	 */
	public void updateSummary(String summary);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptClinicalData(java.lang.Boolean)
	 */
	public void updateAcceptClinicalData(Boolean acceptClinicalData);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptMolecularData(java.lang.Boolean)
	 */
	public void updateAcceptMolecularData(Boolean acceptMolecularData);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptInVitroData(java.lang.Boolean)
	 */
	public void updateAcceptInVitroData(Boolean acceptInVitroData);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptPharmacologyData(java.lang.Boolean)
	 */
	public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#addStudyLink(java.lang.String)
	 */
	public void addStudyLink(String studyEntryURL);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#removeStudyLink(java.lang.String)
	 */
	public void removeStudyLink(String studyEntryURL);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#cancelCreateStudy()
	 */
	public void cancelSaveOrUpdateSubmissionEntry();

	public void saveNewSubmissionEntry();

}