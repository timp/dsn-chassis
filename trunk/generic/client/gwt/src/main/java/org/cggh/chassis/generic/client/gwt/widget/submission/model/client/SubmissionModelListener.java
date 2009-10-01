/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.model.client;

import java.util.Set;

/**
 * @author raok
 *
 */
public interface SubmissionModelListener {

	void onTitleChanged(String before, String after, Boolean isValid);

	void onSummaryChanged(String before, String after, Boolean isValid);

	void onStudyLinksChanged(Set<String> before, Set<String> after, Boolean isValid);

	void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid);

	void onSubmissionEntryChanged(Boolean isValid);

	void onStatusChanged(Integer before, Integer after);

}
