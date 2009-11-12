package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.Set;

import org.cggh.chassis.generic.atom.client.AtomAuthor;


public abstract interface AbstractSubmissionControllerCreateEditAPI {
	
	public void updateAuthors(Set<AtomAuthor> authors);
	
	public void updateTitle(String title);

	public void updateSummary(String summary);

	public void updateModules(Set<String> modules);

	public void addStudyLink(String studyEntryUrl);

	public void removeStudyLink(String studyEntryUrl);

	public void cancelCreateOrUpdateSubmissionEntry();

}