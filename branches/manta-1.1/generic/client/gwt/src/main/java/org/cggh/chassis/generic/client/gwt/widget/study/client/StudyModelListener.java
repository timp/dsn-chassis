/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.Set;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;




/**
 * @author raok
 *
 */
public interface StudyModelListener {

	void onTitleChanged(String before, String after, Boolean isValid);

	void onSummaryChanged(String before, String after, Boolean isValid);

	void onStudyEntryChanged(StudyEntry studyEntry, Boolean isValid);

	void onStatusChanged(Integer before, Integer after);

	void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid);

	void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after, Boolean isValid);

	void onCreatedChanged(String before, String created);

	void onUpdatedChanged(String before, String created);

	/**
	 * @param before
	 * @param id
	 */
	void onTitleChanged(String before, String id);

}
