/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.List;

import org.cggh.chassis.generic.atom.rewrite.client.submission.SubmissionEntry;


/**
 * @author raok
 *
 */
public interface ViewSubmissionsWidgetModelListener {

	void onStatusChanged(Integer before, Integer after);

	void onSubmissionEntriesChanged(List<SubmissionEntry> before, List<SubmissionEntry> after);

}
