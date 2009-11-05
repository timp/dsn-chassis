/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.List;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;


/**
 * @author raok
 *
 */
public interface ViewSubmissionsWidgetModelListener {

	void onStatusChanged(Integer before, Integer after);

	void onSubmissionEntriesChanged(List<SubmissionEntry> before, List<SubmissionEntry> after);

}
