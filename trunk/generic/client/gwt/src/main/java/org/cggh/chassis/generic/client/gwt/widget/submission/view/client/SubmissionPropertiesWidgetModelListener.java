/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.widget.client.ChangeEvent;


/**
 * @author aliman
 *
 */
public interface SubmissionPropertiesWidgetModelListener {

	/**
	 * @param e
	 */
	void onSubmissionEntryChanged(ChangeEvent<SubmissionEntry> e);

}
