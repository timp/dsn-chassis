/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.model.client;

import java.util.Set;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;


/**
 * @author raok
 *
 */
public interface StudyModelListener {

	void onTitleChanged(String before, String after, Boolean isValid);

	void onSummaryChanged(String before, String after, Boolean isValid);

	void onStudyEntryChanged(Boolean isValid);

	void onStatusChanged(Integer before, Integer after);

	void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid);

	void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after, Boolean isValid);

}
