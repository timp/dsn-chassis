/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;

/**
 * @author raok
 *
 */
public interface ViewSubmissionDataFilesWidgetModelListener {

	void onStatusChanged(Integer before, Integer after);

	void onDataFileAtomEntriesChanged(List<AtomEntry> before, List<AtomEntry> after);

}
