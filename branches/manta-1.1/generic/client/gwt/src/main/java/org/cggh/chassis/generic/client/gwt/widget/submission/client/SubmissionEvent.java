/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;



import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomui.client.EventWithAtomEntry;

import com.google.gwt.event.shared.EventHandler;

public abstract class SubmissionEvent
	<H extends EventHandler>
	extends EventWithAtomEntry<H, SubmissionEntry> {
	
	public SubmissionEvent() {}
	
}