package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.Set;

import org.cggh.chassis.generic.atom.client.AtomAuthor;



public abstract interface AbstractStudyControllerCreateEditAPI {

	public void updateTitle(String title);

	public void updateSummary(String summary);

	public void updateModules(Set<String> modules);
	
	public void updateAuthors(Set<AtomAuthor> authors);

	public void cancelSaveOrUpdateStudyEntry();

}