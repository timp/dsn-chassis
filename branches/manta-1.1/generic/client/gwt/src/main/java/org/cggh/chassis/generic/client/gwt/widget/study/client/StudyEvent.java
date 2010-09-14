/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;



import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomui.client.EventWithAtomEntry;

import com.google.gwt.event.shared.EventHandler;

public abstract class StudyEvent
	<H extends EventHandler>
	extends EventWithAtomEntry<H, StudyEntry> {
	
	public StudyEvent() {}
	
}