package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import java.util.Set;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;


public abstract interface AbstractSubmissionControllerCreateEditAPI {
	
	public void updateAuthors(Set<AtomAuthor> authors);
	
	public void updateTitle(String title);

	public void updateSummary(String summary);

	public void updateModules(Set<String> modules);

	public void addStudyLink(String studyEntryURL);

	public void removeStudyLink(String studyEntryURL);

	public void cancelCreateOrUpdateSubmissionEntry();

}